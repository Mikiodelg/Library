package edu.upc.eetac.dsa.mdelgado.libraryproject.libraryapi;

import javax.ws.rs.GET;

import edu.upc.eetac.dsa.mdelgado.libraryproject.librarymodel.LibraryRootAPI;

public class LibraryRootApiResource {
	@GET
	public LibraryRootAPI getRootAPI() {
		LibraryRootAPI API = new LibraryRootAPI();
		return API;
	}
}
