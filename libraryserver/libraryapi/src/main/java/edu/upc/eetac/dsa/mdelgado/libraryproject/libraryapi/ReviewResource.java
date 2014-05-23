package edu.upc.eetac.dsa.mdelgado.libraryproject.libraryapi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import edu.upc.eetac.dsa.mdelgado.libraryproject.librarymodel.Book;
import edu.upc.eetac.dsa.mdelgado.libraryproject.librarymodel.BookCollection;
import edu.upc.eetac.dsa.mdelgado.libraryproject.librarymodel.Review;
import edu.upc.eetac.dsa.mdelgado.libraryproject.libraryapi.MediaType;
import edu.upc.eetac.dsa.mdelgado.libraryproject.libraryapi.DataSourceSPA;
import edu.upc.eetac.dsa.mdelgado.libraryproject.librarymodel.Book;
import edu.upc.eetac.dsa.mdelgado.libraryproject.librarymodel.BookCollection;
import edu.upc.eetac.dsa.mdelgado.libraryproject.librarymodel.Review;
import edu.upc.eetac.dsa.mdelgado.libraryproject.librarymodel.ReviewCollection;

@Path("/reviews")
public class ReviewResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	@Context
	private SecurityContext security;
	
	@GET
	@Produces(MediaType.LIBRARY_API_REVIEW_COLLECTION)
	public ReviewCollection getReviews() {
		ReviewCollection reviews = new ReviewCollection();
		Connection conn = null;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
			PreparedStatement stmt = null;
		
		try {
			
			stmt = conn.prepareStatement(buildGetReviewsQuery());
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Review review = new Review();
				review.setId(rs.getString("id"));
				review.setIsbn(rs.getString("ISBN"));
				review.setUsername(rs.getString("Username"));
				review.setRealname(rs.getString("Realname"));
				review.setUltEdicion(rs.getString("UltEdicion"));
				review.setCOntent(rs.getString("COntent"));
				reviews.addReview(review);
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
		return reviews;
	}
	
	private String buildGetReviewsQuery() {
		return "select * from review;";
	}
	
	@GET
	@Path("/{isbn}")
	@Produces(MediaType.LIBRARY_API_REVIEW)
	
	public Review getReviewfromdb(@PathParam("isbn") String bookisbn){
	Review review = new Review();
	Connection conn = null;

	try {
		conn = ds.getConnection();
	} catch (SQLException e) {
		throw new ServerErrorException("Could not connect to the database",
				Response.Status.SERVICE_UNAVAILABLE);
	}
	
	PreparedStatement stmt = null;
	try {
		stmt = conn.prepareStatement(buildGetReviewByIsbmQuery());
		stmt.setString(1, bookisbn);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			review.setId(rs.getString("id"));
			review.setIsbn(rs.getString("ISBN"));
			review.setUsername(rs.getString("Username"));
			review.setRealname(rs.getString("Realname"));
			review.setUltEdicion(rs.getString("UltEdicion"));
			review.setCOntent(rs.getString("COntent"));
		}
		else {
			throw new NotFoundException("There's no book with isbn="
					+ bookisbn);
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
	
	return review;
	}
	
	private String buildGetReviewByIsbmQuery() {
		return "select * from review where ISBN=?";
	}
	
	@POST
	@Consumes(MediaType.LIBRARY_API_REVIEW)
	@Produces(MediaType.LIBRARY_API_REVIEW)
	public Review CreateReview(Review review){
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
				
		PreparedStatement stmt = null;
		try {
			String sql = BuildInsertReview();
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, review.getIsbn());
			stmt.setString(2, review.getUsername());
			stmt.setString(3, review.getRealname());
			stmt.setString(4, review.getCOntent());
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				String reviewid = review.getIsbn();
				review = getReviewfromdb(reviewid);
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
		return review;
	}
	
	private String BuildInsertReview(){
		return "insert into review (ISBN, Username, Realname, COntent) values (?,?,?,?)";
	}
	
	@PUT
	@Path("/{bookisbn}")
	@Consumes(MediaType.LIBRARY_API_REVIEW)
	@Produces(MediaType.LIBRARY_API_REVIEW)
	public Review UpdateReview(@PathParam("bookisbn") String bookisbn, Review review){
		Connection conn = null;
		
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
				
		PreparedStatement stmt = null;
		try {
			
			String sql = BuildUpdateReview();
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, review.getCOntent());
			stmt.setString(2, bookisbn);
			stmt.setString(3, review.getUsername());
			
				int rows = stmt.executeUpdate();
			
			
			if (rows != 0){
					review = getReviewfromdb(bookisbn);
			}
			else {
				throw new NotFoundException("There's no book with bookid="
						+ bookisbn);// Updating inexistent sting
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
		
		return review;
	}
	private String BuildUpdateReview(){
		return "update review set COntent=ifnull(?, COntent) where ISBN=? and Username=?";
		}
	
	@DELETE
	@Path("/{bookisbn}")
	public void DeleteBook(@PathParam("bookisbn") String bookisbn){
		Connection conn = null;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		
		PreparedStatement stmt = null;
		try {
			
			String sql = BuildDeleteReview();
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			 
			stmt.setString(1, bookisbn);
			stmt.setString(2, "Miki");
			int rows = stmt.executeUpdate();
			if (rows == 0){
				throw new NotFoundException("There's no book with bookid="
						+ bookisbn);// Updating inexistent sting
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
	private String BuildDeleteReview(){
		return "delete from review where ISBN=? and Username=?";
		
	}
	
}
