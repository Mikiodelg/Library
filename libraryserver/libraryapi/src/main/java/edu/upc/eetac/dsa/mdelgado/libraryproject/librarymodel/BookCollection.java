package edu.upc.eetac.dsa.mdelgado.libraryproject.librarymodel;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.mdelgado.libraryproject.librarymodel.Book;
import edu.upc.eetac.dsa.mdelgado.libraryproject.libraryapi.MediaType;
import edu.upc.eetac.dsa.mdelgado.libraryproject.libraryapi.BookResource;


import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;

public class BookCollection {
	
	@InjectLinks({
	@InjectLink(resource = BookResource.class, style = Style.ABSOLUTE, rel = "create-book", title = "Create book", type = MediaType.LIBRARY_API_BOOK),
//	@InjectLink(value = "/books?before={before}", style = Style.ABSOLUTE, rel = "previous", title = "Previous book", type = MediaType.LIBRARY_API_BOOK_COLLECTION, bindings = { @Binding(name = "before", value = "${instance.oldestimpresion}") }),
//	@InjectLink(value = "/books?after={after}", style = Style.ABSOLUTE, rel = "current", title = "Newest book", type = MediaType.LIBRARY_API_BOOK_COLLECTION, bindings = { @Binding(name = "after", value = "${instance.newestimpresion") }) 
	})

	private List<Link> links;
	private List<Book> books;
	private long newestImpresion;
	private long oldestImpresion;
	
	public BookCollection(){
		super();
		books = new ArrayList<>();
	}
	
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	public long getNewestImpresion() {
		return newestImpresion;
	}
	public void setNewestImpresion(long newestImpresion) {
		this.newestImpresion = newestImpresion;
	}
	public long getOldestImpresion() {
		return oldestImpresion;
	}
	public void setOldestImpresion(long oldestImpresion) {
		this.oldestImpresion = oldestImpresion;
	}
	public void addBooks(Book book){
		books.add(book);
		
	}

}
