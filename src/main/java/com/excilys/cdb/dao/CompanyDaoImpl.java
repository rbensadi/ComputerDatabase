package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.pojo.Company;

public enum CompanyDaoImpl implements ICompanyDao {

	INSTANCE;

	private static final String SQL_FIND_BY_ID = "SELECT id,name FROM company WHERE id = ?";

	private DaoFactory daoFactory;

	private CompanyDaoImpl() {
		this.daoFactory = DaoFactory.INSTANCE;
	}

	public Company findById(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company = null;

		connection = daoFactory.getConnection();
		try {
			preparedStatement = DaoUtils.getPreparedStatement(connection,
					SQL_FIND_BY_ID, false, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				company = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DaoException("CompanyDao@findById() failed !", e);
		} finally {
			DaoUtils.silentClosing(connection, preparedStatement, resultSet);
		}

		return company;
	}

	private Company map(ResultSet resultSet) throws SQLException {
		Company company = new Company();

		company.setId(resultSet.getInt(ID_FIELD));
		company.setName(resultSet.getString(NAME_FIELD));

		return company;
	}

}
