package edu.upc.eetac.dsa.mdelgado.libraryproject.librarymodel;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.mdelgado.libraryproject.librarymodel.Book;
import edu.upc.eetac.dsa.mdelgado.libraryproject.librarymodel.Review;
import edu.upc.eetac.dsa.mdelgado.libraryproject.libraryapi.MediaType;
import edu.upc.eetac.dsa.mdelgado.libraryproject.libraryapi.BookResource;
import edu.upc.eetac.dsa.mdelgado.libraryproject.libraryapi.ReviewResource;



import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;

import javax.ws.rs.core.Link;

public class ReviewCollection {


	
	private List<Link> links;
	private List<Review> Reviews;
	private long newestTimestamp;
	private long oldestTimestamp;
	
	public ReviewCollection(){
		super();
		Reviews = new ArrayList<>();
	}
	
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public List<Review> getReviews() {
		return Reviews;
	}
	public void setReviews(List<Review> reviews) {
		Reviews = reviews;
	}
	public long getNewestTimestamp() {
		return newestTimestamp;
	}
	public void setNewestTimestamp(long newestTimestamp) {
		this.newestTimestamp = newestTimestamp;
	}
	public long getOldestTimestamp() {
		return oldestTimestamp;
	}
	public void setOldestTimestamp(long oldestTimestamp) {
		this.oldestTimestamp = oldestTimestamp;
	}

	public void addReview(Review review){
		this.Reviews.add(review);
	}
}
