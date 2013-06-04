package com.excilys.cdb.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class DaoFactory {

	private static final String PROPERTIES_FILE = "/com/excilys/cdb/dao/dao.properties";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_USER = "user";
	private static final String PROPERTY_PASSWORD = "password";

	private String url;
	private String driver;
	private String user;
	private String password;
	private ThreadLocal<Connection> threadLocalConnection;

	private DaoFactory() {
		threadLocalConnection = new ThreadLocal<Connection>();

		Properties properties = new Properties();

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream fichierProperties = classLoader
				.getResourceAsStream(PROPERTIES_FILE);

		if (fichierProperties == null) {
			StringBuilder sb = new StringBuilder();
			sb.append("The properties file ");
			sb.append(PROPERTIES_FILE);
			sb.append(" is unfindable !");
			throw new DaoConfigurationException(sb.toString());
		}

		try {
			properties.load(fichierProperties);
			url = properties.getProperty(PROPERTY_URL);
			driver = properties.getProperty(PROPERTY_DRIVER);
			user = properties.getProperty(PROPERTY_USER);
			password = properties.getProperty(PROPERTY_PASSWORD);
		} catch (IOException e) {
			StringBuilder sb = new StringBuilder();
			sb.append("Impossible to load the proterties file ");
			sb.append(PROPERTIES_FILE);
			throw new DaoConfigurationException(sb.toString(), e);
		}

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		Connection connection = threadLocalConnection.get();
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(url, user, password);
				threadLocalConnection.set(connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	public void closeConnection() {
		DaoUtils.silentClosing(threadLocalConnection.get());
		threadLocalConnection.remove();
	}
}
