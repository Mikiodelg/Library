package edu.upc.eetac.dsa.mdelgado.libraryproject.librarymodel;

	import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import edu.upc.eetac.dsa.mdelgado.libraryproject.libraryapi.MediaType;
import edu.upc.eetac.dsa.mdelgado.libraryproject.libraryapi.BookResource;
import edu.upc.eetac.dsa.mdelgado.libraryproject.libraryapi.ReviewResource;

	public class Book {
		
		@InjectLinks({
			@InjectLink(resource = BookResource.class, style = Style.ABSOLUTE, rel = "book", title = "Latest books", type = MediaType.LIBRARY_API_BOOK_COLLECTION),
			@InjectLink(resource = BookResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "book", type = MediaType.LIBRARY_API_BOOK, method = "getBookfromdb", bindings = @Binding(name = "isbn", value = "${instance.isbn}")),
			@InjectLink(resource = ReviewResource.class, style = Style.ABSOLUTE, rel = "review", title = "review", type = MediaType.LIBRARY_API_REVIEW_COLLECTION, method = "getReviews", bindings = @Binding(name = "isbn", value = "${instance.isbn}")) })
		
		private List<Link> links;
		private String id;
		private String isbn;
		private String title;
		private String autor;
		private String lengua;
		private String edicion;
		private String fechaedicion;
		private String fechaimpresion;
		private String editorial;
		
		public List<Link> getLinks() {
			return links;
		}
		public void setLinks(List<Link> links) {
			this.links = links;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getIsbn() {
			return isbn;
		}
		public void setIsbn(String isbn) {
			this.isbn = isbn;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getAutor() {
			return autor;
		}
		public void setAutor(String autor) {
			this.autor = autor;
		}
		public String getLengua() {
			return lengua;
		}
		public void setLengua(String lengua) {
			this.lengua = lengua;
		}
		public String getEdicion() {
			return edicion;
		}
		public void setEdicion(String edicion) {
			this.edicion = edicion;
		}
		public String getFechaedicion() {
			return fechaedicion;
		}
		public void setFechaedicion(String fechaedicion) {
			this.fechaedicion = fechaedicion;
		}
		public String getFechaimpresion() {
			return fechaimpresion;
		}
		public void setFechaimpresion(String fechaimpresion) {
			this.fechaimpresion = fechaimpresion;
		}
		public String getEditorial() {
			return editorial;
		}
		public void setEditorial(String editorial) {
			this.editorial = editorial;
		}
		
}
