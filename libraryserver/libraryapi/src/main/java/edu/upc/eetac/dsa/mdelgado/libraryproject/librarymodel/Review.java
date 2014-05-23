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

public class Review {
	
	@InjectLinks({
		@InjectLink(resource = ReviewResource.class, style = Style.ABSOLUTE, rel = "review", title = "Latest review", type = MediaType.LIBRARY_API_REVIEW_COLLECTION)})
//		@InjectLink(resource = ReviewResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "review", type = MediaType.LIBRARY_API_REVIEW, method = "getReviewfromdb", bindings = @Binding(name = "isbn", value = "${instance.isbn}")) })

	private List<Link> links;
	private String id;
	private String isbn;
	private String Username;
	private String Realname;
	private String UltEdicion;
	private String COntent;
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
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getRealname() {
		return Realname;
	}
	public void setRealname(String realname) {
		Realname = realname;
	}
	public String getUltEdicion() {
		return UltEdicion;
	}
	public void setUltEdicion(String ultEdicion) {
		UltEdicion = ultEdicion;
	}
	public String getCOntent() {
		return COntent;
	}
	public void setCOntent(String cOntent) {
		COntent = cOntent;
	}
	
}
