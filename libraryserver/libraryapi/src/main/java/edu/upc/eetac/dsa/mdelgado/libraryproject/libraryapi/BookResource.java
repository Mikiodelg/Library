package edu.upc.eetac.dsa.mdelgado.libraryproject.libraryapi;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;


import edu.upc.eetac.dsa.mdelgado.libraryproject.libraryapi.MediaType;
import edu.upc.eetac.dsa.mdelgado.libraryproject.libraryapi.DataSourceSPA;
import edu.upc.eetac.dsa.mdelgado.libraryproject.librarymodel.Book;
import edu.upc.eetac.dsa.mdelgado.libraryproject.librarymodel.BookCollection;

@Path("/books")
public class BookResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();

	@Context
	private SecurityContext security;

	@GET
	@Produces(MediaType.LIBRARY_API_BOOK_COLLECTION)
	public BookCollection getBooks() {
		BookCollection books = new BookCollection();
		Connection conn = null;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = conn.prepareStatement(buildGetBooksQuery());
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				Book book = new Book();
				book.setId(rs.getString("id"));
				book.setIsbn(rs.getString("ISBN"));
				book.setTitle(rs.getString("Title"));
				book.setAutor(rs.getString("Autor"));
				book.setLengua(rs.getString("Lengua"));
				book.setEdicion(rs.getString("Lengua"));
				book.setFechaedicion(rs.getString("Fechaedicion"));
				book.setFechaimpresion(rs.getString("Fechaimpresion"));
				book.setEditorial(rs.getString("Editorial"));
				books.addBooks(book);
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		return books;

	}

	private String buildGetBooksQuery() {
		return "select * from bookfile;";
	}
	
	//////////////////////////////
	
		@GET
		@Path("/{bookisbn}")
		@Produces(MediaType.LIBRARY_API_BOOK)
		//GetBook Cacheable//
		public Response getBook(@PathParam("bookisbn") String bookisbn,
			@Context Request request) {
			//Codigo para respuesta cacheable//
			CacheControl cc = new CacheControl();
			
			//Cambiar esto por el tipo del archivo a devolver cacheable//
			Book book = getBookfromdb(bookisbn);
			//Cambiar esto por el tipo del archivo a devolver cacheable//
			
			EntityTag eTag = new EntityTag(book.getFechaimpresion());
			Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);
			if (rb != null) {
				return rb.cacheControl(cc).tag(eTag).build();
			}
			rb = Response.ok(book).cacheControl(cc).tag(eTag);
			return rb.build();
			//Fin codigo para respuesta cacheable//
		}
		
		public Book getBookfromdb(String isbn){
		Book book = new Book();
		Connection conn = null;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(buildGetBookByIsbmQuery());
			stmt.setString(1, isbn);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				book.setId(rs.getString("id"));
				book.setIsbn(rs.getString("ISBN"));
				book.setTitle(rs.getString("Title"));
				book.setAutor(rs.getString("Autor"));
				book.setLengua(rs.getString("Lengua"));
				book.setEdicion(rs.getString("Lengua"));
				book.setFechaedicion(rs.getString("Fechaedicion"));
				book.setFechaimpresion(rs.getString("Fechaimpresion"));
				book.setEditorial(rs.getString("Editorial"));
			}
			else {
				throw new NotFoundException("There's no book with isbn="
						+ isbn);
			}
			
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		
		return book;
		}
		
		private String buildGetBookByIsbmQuery() {
			return "select * from bookfile where ISBN=?";
		}
		
	//////////////////////////////
		
		@GET
		@Path("/search")
		@Produces(MediaType.LIBRARY_API_BOOK_COLLECTION)
		public BookCollection Search(@QueryParam("titulo") String titulo,
				@QueryParam("autor") String autor,@QueryParam("length") int length){
			
			BookCollection books = new BookCollection();
			Connection conn = null;

			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServerErrorException("Could not connect to the database",
						Response.Status.SERVICE_UNAVAILABLE);
			}
			
			PreparedStatement stmt = null;
			
			try {
				stmt = conn.prepareStatement(buildSearchBooksQuery());

				stmt.setString(1, "%"+autor+"%");
				stmt.setString(2, "%"+titulo+"%");
				System.out.println("lenght 1:" + length);
				length = (length <= 0) ? 20 : length;
				System.out.println("lenght 2:" + length);
				stmt.setInt(3, length);
				
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					Book book = new Book();
					book.setId(rs.getString("id"));
					book.setIsbn(rs.getString("ISBN"));
					book.setTitle(rs.getString("Title"));
					book.setAutor(rs.getString("Autor"));
					book.setLengua(rs.getString("Lengua"));
					book.setEdicion(rs.getString("Lengua"));
					book.setFechaedicion(rs.getString("Fechaedicion"));
					book.setFechaimpresion(rs.getString("Fechaimpresion"));
					book.setEditorial(rs.getString("Editorial"));
					books.addBooks(book);
				}
			} catch (SQLException e) {
				throw new ServerErrorException(e.getMessage(),
						Response.Status.INTERNAL_SERVER_ERROR);
			} finally {
				try {
					if (stmt != null)
						stmt.close();
					conn.close();
				} catch (SQLException e) {
				}
			}
		 
			return books;
			}
		
		private String buildSearchBooksQuery() {
			
			return "select * from bookfile where Autor like ? or Title like ? limit ?";
			
	}	
		
	@POST
	@Consumes(MediaType.LIBRARY_API_BOOK)
	@Produces(MediaType.LIBRARY_API_BOOK)
	public Book CreateBook(Book book){
		
		Connection conn = null;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		
		PreparedStatement stmt = null;
		try {
			
			String sql = BuildInsertBook();
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			 
			stmt.setString(1, book.getIsbn());
			stmt.setString(2, book.getTitle());
			stmt.setString(3, book.getAutor());
			stmt.setString(4, book.getLengua());
			stmt.setString(5, book.getEdicion());
			stmt.setString(6, book.getFechaedicion());
			stmt.setString(7, book.getFechaimpresion());
			stmt.setString(8, book.getEditorial());
			
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				String bookid = rs.getString(1);
				book = getBookfromdb(bookid);
			}
		}catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		
		return book;
	}
	private String BuildInsertBook(){
		return "insert into bookfile (ISBN, Title, Autor, Lengua, Edicion, FechaEdicion, FechaImpresion, Editorial) values (?,?,?,?,?,?,?,?)";
		
	}
	
	@PUT
	@Path("/{bookid}")
	@Consumes(MediaType.LIBRARY_API_BOOK)
	@Produces(MediaType.LIBRARY_API_BOOK)
	public Book UpdateBook(@PathParam("bookid") String bookid, Book book){
		Connection conn = null;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		
		PreparedStatement stmt = null;
		try {
			
			String sql = BuildUpdateBook();
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			 
			stmt.setString(1, book.getIsbn());
			stmt.setString(2, book.getTitle());
			stmt.setString(3, book.getAutor());
			stmt.setString(4, book.getLengua());
			stmt.setString(5, book.getEdicion());
			stmt.setString(6, book.getFechaedicion());
			stmt.setString(7, book.getFechaimpresion());
			stmt.setString(8, book.getEditorial());
			stmt.setInt(9, Integer.valueOf(bookid));
			
			int rows = stmt.executeUpdate();
			
			
			if (rows == 1){
					book = getBookfromdb(bookid);
			}
			else {
				throw new NotFoundException("There's no book with bookid="
						+ bookid);// Updating inexistent sting
			}
			
			
		}catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		
		return book;
	}
	private String BuildUpdateBook(){
		return "update bookfile set ISBN=ifnull(?, ISBN), Title=ifnull(?, Title), Autor=ifnull(?, Autor), Lengua=ifnull(?, Lengua), Edicion=ifnull(?, Edicion), FechaEdicion=ifnull(?, FechaEdicion), FechaImpresion=ifnull(?, FechaImpresion), Editorial=ifnull(?, Editorial) where ID=?";
		
	}
	
	@DELETE
	@Path("/{bookid}")
	public void DeleteBook(@PathParam("bookid") String bookid){
		
		Connection conn = null;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		
		PreparedStatement stmt = null;
		try {
			
			String sql = BuildDeleteBook();
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			 
			stmt.setInt(1, Integer.valueOf(bookid));
			int rows = stmt.executeUpdate();
			
			if (rows == 0){
				throw new NotFoundException("There's no book with bookid="
						+ bookid);// Updating inexistent sting
			}	
		}
			catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	}
	private String BuildDeleteBook(){
		return "delete from bookfile where ID=?";
		
	}
	
			
}
