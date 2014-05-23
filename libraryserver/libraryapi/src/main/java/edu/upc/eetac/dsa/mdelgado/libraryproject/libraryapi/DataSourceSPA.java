package edu.upc.eetac.dsa.mdelgado.libraryproject.libraryapi;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.upc.eetac.dsa.mdelgado.libraryproject.libraryapi.DataSourceSPA;

public class DataSourceSPA {
	private DataSource dataSource;
	private static DataSourceSPA instance;

	private DataSourceSPA() {
		super();
		Context envContext = null;
		try {
			envContext = new InitialContext();
			Context initContext = (Context) envContext.lookup("java:/comp/env");
			dataSource = (DataSource) initContext.lookup("jdbc/library");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
	}

	public final static DataSourceSPA getInstance() {
		if (instance == null)
			instance = new DataSourceSPA();
		return instance;
	}

	public DataSource getDataSource() {
		return dataSource;
	}
}
