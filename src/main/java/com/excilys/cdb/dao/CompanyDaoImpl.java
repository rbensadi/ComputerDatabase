package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.pojo.Company;

public enum CompanyDaoImpl implements ICompanyDao {

	INSTANCE;

	private static final String SQL_FIND = "SELECT id,name FROM company WHERE id = ?";
	private static final String SQL_LIST = "SELECT id,name FROM company ORDER BY name";

	private DaoFactory daoFactory;

	private CompanyDaoImpl() {
		this.daoFactory = DaoFactory.INSTANCE;
	}

	public Company find(int id) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company = null;

		connection = daoFactory.getConnection();
		try {
			preparedStatement = DaoUtils.getPreparedStatement(connection,
					SQL_FIND, false, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				company = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DaoException("CompanyDao@find() failed !", e);
		} finally {
			DaoUtils.silentClosing(preparedStatement, resultSet);
		}

		return company;
	}

	public List<Company> list() throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Company> companies = new ArrayList<Company>();

		connection = daoFactory.getConnection();
		try {
			preparedStatement = DaoUtils.getPreparedStatement(connection,
					SQL_LIST, false);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				companies.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DaoException("CompanyDao@list() failed !", e);
		} finally {
			DaoUtils.silentClosing(preparedStatement, resultSet);
		}

		return companies;
	}

	private Company map(ResultSet resultSet) throws SQLException {
		Company company = new Company();

		company.setId(resultSet.getInt(ID_FIELD));
		company.setName(resultSet.getString(NAME_FIELD));

		return company;
	}

}
