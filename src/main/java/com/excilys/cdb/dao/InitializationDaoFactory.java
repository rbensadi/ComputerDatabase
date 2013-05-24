package com.excilys.cdb.dao;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitializationDaoFactory implements ServletContextListener {

	public static final String ATT_DAO_FACTORY = "daoFactory";

	private DaoFactory daoFactory;

	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		this.daoFactory = DaoFactory.getInstance();
		servletContext.setAttribute(ATT_DAO_FACTORY, this.daoFactory);
	}

	public void contextDestroyed(ServletContextEvent event) {
		// Do nothing
	}

}
