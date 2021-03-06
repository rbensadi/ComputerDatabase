package com.excilys.cdb.dao;

import java.sql.*;

public class DaoUtils {

	public static PreparedStatement getPreparedStatement(Connection connection,
			String query, boolean generatedKey, Object... objects)
			throws SQLException {
		int autoGeneratedKeys;

		if (generatedKey) {
			autoGeneratedKeys = Statement.RETURN_GENERATED_KEYS;
		} else {
			autoGeneratedKeys = Statement.NO_GENERATED_KEYS;
		}

		PreparedStatement preparedStatement = connection.prepareStatement(
				query, autoGeneratedKeys);

		for (int i = 0; i < objects.length; i++) {
			preparedStatement.setObject(i + 1, objects[i]);
		}

		return preparedStatement;
	}

	public static void silentClosing(PreparedStatement preparedStatement) {
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void silentClosing(PreparedStatement preparedStatement,
			ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		silentClosing(preparedStatement);
	}

	public static void silentClosing(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
