package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.pojo.Computer;

public enum ComputerDaoImpl implements IComputerDao {

	INSTANCE;

	private static final String SQL_INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private static final String SQL_FIND_BY_ID = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id = ?";
	// private static final String SQL_FIND_BY_NAME =
	// "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE name = ?";
	private static final String SQL_COUNT = "SELECT COUNT(*) FROM computer WHERE name LIKE ?";
	private static final String SQL_LIST = "SELECT id,name,introduced,discontinued,company_id FROM computer ORDER BY name";
	private static final String SQL_SUB_LIST = "SELECT id,name,introduced,discontinued,company_id FROM computer ORDER BY name LIMIT ? OFFSET ?";
	private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM computer where id = ?";
	private static final String SQL_FILTER_BY_NAME = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE name LIKE ? ORDER BY name LIMIT ? OFFSET ?";

	private DaoFactory daoFactory;
	private ICompanyDao companyDao;

	private ComputerDaoImpl() {
		daoFactory = DaoFactory.INSTANCE;
		companyDao = CompanyDaoImpl.INSTANCE;
	}

	public int insert(Computer computer) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int id = -1;

		connection = daoFactory.getConnection();
		try {
			preparedStatement = DaoUtils.getPreparedStatement(connection,
					SQL_INSERT, true, computer.getName(),
					computer.getIntroduced(), computer.getDiscontinued(),
					computer.getCompany().getId());
			int result = preparedStatement.executeUpdate();
			if (result != 1) {
				throw new DaoException("ComputerDao@insert() failed !");
			}
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			throw new DaoException("ComputerDao@insert() failed !", e);
		} finally {
			DaoUtils.silentClosing(connection, preparedStatement, resultSet);
		}

		return id;
	}

	public Computer findById(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Computer computer = null;

		connection = daoFactory.getConnection();
		try {
			preparedStatement = DaoUtils.getPreparedStatement(connection,
					SQL_FIND_BY_ID, false, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				computer = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DaoException("ComputerDao@findById() failed !", e);
		} finally {
			DaoUtils.silentClosing(connection, preparedStatement, resultSet);
		}

		return computer;
	}

	public Computer findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public int numberOfComputers(String filter) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int count = 0;

		StringBuilder sb = new StringBuilder();
		sb.append("%");
		sb.append(filter);
		sb.append("%");

		connection = daoFactory.getConnection();
		try {
			preparedStatement = DaoUtils.getPreparedStatement(connection,
					SQL_COUNT, false, sb.toString());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			throw new DaoException("ComputerDao@list() failed !", e);
		} finally {
			DaoUtils.silentClosing(connection, preparedStatement, resultSet);
		}

		return count;
	}

	public List<Computer> list() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Computer> computers = new ArrayList<Computer>();

		connection = daoFactory.getConnection();
		try {
			preparedStatement = DaoUtils.getPreparedStatement(connection,
					SQL_LIST, false);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				computers.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DaoException("ComputerDao@list() failed !", e);
		} finally {
			DaoUtils.silentClosing(connection, preparedStatement, resultSet);
		}

		return computers;
	}

	public List<Computer> list(int limit, int offset) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Computer> computers = new ArrayList<Computer>();

		connection = daoFactory.getConnection();
		try {
			preparedStatement = DaoUtils.getPreparedStatement(connection,
					SQL_SUB_LIST, false, limit, offset);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				computers.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DaoException("ComputerDao@list() failed !", e);
		} finally {
			DaoUtils.silentClosing(connection, preparedStatement, resultSet);
		}

		return computers;
	}

	public void update(Computer computer) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		connection = daoFactory.getConnection();
		try {
			preparedStatement = DaoUtils.getPreparedStatement(connection,
					SQL_UPDATE, false, computer.getName(),
					computer.getIntroduced(), computer.getDiscontinued(),
					computer.getCompany().getId(), computer.getId());
			int result = preparedStatement.executeUpdate();
			if (result != 1) {
				throw new DaoException("ComputerDao@update() failed !");
			}
		} catch (SQLException e) {
			throw new DaoException("ComputerDao@update() failed !", e);
		} finally {
			DaoUtils.silentClosing(connection, preparedStatement);
		}
	}

	public void deleteById(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		connection = daoFactory.getConnection();
		try {
			preparedStatement = DaoUtils.getPreparedStatement(connection,
					SQL_DELETE_BY_ID, false, id);
			int result = preparedStatement.executeUpdate();
			if (result != 1) {
				throw new DaoException("ComputerDao@deleteById() failed !");
			}
		} catch (SQLException e) {
			throw new DaoException("ComputerDao@deleteById() failed !", e);
		} finally {
			DaoUtils.silentClosing(connection, preparedStatement);
		}
	}

	private Computer map(ResultSet resultSet) throws SQLException {
		Computer computer = new Computer();

		computer.setId(resultSet.getInt(ID_FIELD));
		computer.setName(resultSet.getString(NAME_FIELD));
		computer.setIntroduced(resultSet.getDate(INTRODUCED_FIELD));
		computer.setDiscontinued(resultSet.getDate(DISCOUNTINUED_FIELD));
		Object o = resultSet.getObject(ID_COMPANY_FIELD);
		if (o == null) {
			computer.setCompany(null);
		} else {
			computer.setCompany(companyDao.findById(resultSet
					.getInt(ID_COMPANY_FIELD)));
		}

		return computer;
	}

	public List<Computer> filterByName(String filter, int limit, int offset) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Computer> computers = new ArrayList<Computer>();

		StringBuilder sb = new StringBuilder();
		sb.append("%");
		sb.append(filter);
		sb.append("%");

		connection = daoFactory.getConnection();
		try {
			preparedStatement = DaoUtils.getPreparedStatement(connection,
					SQL_FILTER_BY_NAME, false, sb.toString(), limit, offset);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				computers.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DaoException("ComputerDao@filterByName() failed !", e);
		} finally {
			DaoUtils.silentClosing(connection, preparedStatement, resultSet);
		}

		return computers;
	}
}
