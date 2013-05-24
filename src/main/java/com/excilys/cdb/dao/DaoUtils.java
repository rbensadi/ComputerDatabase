package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoUtils {

	public static PreparedStatement getPreparedStatement(Connection connection,
			String request, Object... objects) throws SQLException {
		PreparedStatement preparedStatement = connection
				.prepareStatement(request);
		for (int i = 0; i < objects.length; i++) {
			preparedStatement.setObject(i + 1, objects[i]);
		}
		return preparedStatement;
	}

	public static void silentClosing(Connection connection,
			PreparedStatement preparedStatement) {
		try {
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void silentClosing(Connection connection,
			PreparedStatement preparedStatement, ResultSet resultSet) {
		try {
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		silentClosing(connection, preparedStatement);
	}
}
