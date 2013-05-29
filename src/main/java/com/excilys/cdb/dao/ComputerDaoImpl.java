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
	private static final String SQL_FIND = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id = ?";
	private static final String SQL_COUNT = "SELECT COUNT(*) FROM computer WHERE name LIKE ?";
	private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private static final String SQL_DELETE = "DELETE FROM computer where id = ?";
	private static final String SQL_LIST_PART1 = "SELECT computer.id,computer.name,computer.introduced,computer.discontinued,computer.company_id FROM computer";
	private static final String SQL_LIST_PART2 = " WHERE computer.name LIKE ? ORDER BY ISNULL(";
	private static final String SQL_LIST_PART3 = " LIMIT ? OFFSET ?";

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

		Integer companyId = computer.getCompany() == null ? null : computer
				.getCompany().getId();

		connection = daoFactory.getConnection();
		try {
			preparedStatement = DaoUtils.getPreparedStatement(connection,
					SQL_INSERT, true, computer.getName(),
					computer.getIntroduced(), computer.getDiscontinued(),
					companyId);
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
			DaoUtils.silentClosing(preparedStatement, resultSet);
		}

		return id;
	}

	public Computer find(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Computer computer = null;

		connection = daoFactory.getConnection();
		try {
			preparedStatement = DaoUtils.getPreparedStatement(connection,
					SQL_FIND, false, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				computer = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DaoException("ComputerDao@findById() failed !", e);
		} finally {
			DaoUtils.silentClosing(preparedStatement, resultSet);
		}

		return computer;
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
			DaoUtils.silentClosing(preparedStatement, resultSet);
		}

		return count;
	}

	public void update(Computer computer) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Integer companyId = computer.getCompany() == null ? null : computer
				.getCompany().getId();

		connection = daoFactory.getConnection();
		try {
			preparedStatement = DaoUtils.getPreparedStatement(connection,
					SQL_UPDATE, false, computer.getName(),
					computer.getIntroduced(), computer.getDiscontinued(),
					companyId, computer.getId());
			int result = preparedStatement.executeUpdate();
			if (result != 1) {
				throw new DaoException("ComputerDao@update() failed !");
			}
		} catch (SQLException e) {
			throw new DaoException("ComputerDao@update() failed !", e);
		} finally {
			DaoUtils.silentClosing(preparedStatement);
		}
	}

	public void delete(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		connection = daoFactory.getConnection();
		try {
			preparedStatement = DaoUtils.getPreparedStatement(connection,
					SQL_DELETE, false, id);
			int result = preparedStatement.executeUpdate();
			if (result != 1) {
				throw new DaoException("ComputerDao@deleteById() failed !");
			}
		} catch (SQLException e) {
			throw new DaoException("ComputerDao@deleteById() failed !", e);
		} finally {
			DaoUtils.silentClosing(preparedStatement);
		}
	}

	public List<Computer> list(String filterByName, String sortedColumn,
			String order, int limit, int offset) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Computer> computers = new ArrayList<Computer>();

		StringBuilder sb = new StringBuilder();
		sb.append("%");
		sb.append(filterByName);
		sb.append("%");

		connection = daoFactory.getConnection();
		try {
			preparedStatement = DaoUtils.getPreparedStatement(connection,
					getOrderForQuery(sortedColumn, order), false,
					sb.toString(), limit, offset);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				computers.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DaoException("ComputerDao@sortedByColumn() failed !", e);
		} finally {
			DaoUtils.silentClosing(preparedStatement, resultSet);
		}

		return computers;
	}

	private static String getOrderForQuery(String columnName, String order) {
		StringBuilder sb = new StringBuilder();
		sb.append(SQL_LIST_PART1);

		if (columnName.equals(ICompanyDao.NAME_FIELD)) {
			sb.append(" LEFT JOIN company ON computer.company_id = company.id");
		}

		sb.append(SQL_LIST_PART2);
		sb.append(columnName);
		sb.append("),");
		sb.append(columnName);
		sb.append(" ");
		sb.append(order);
		sb.append(SQL_LIST_PART3);

		return sb.toString();
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
			computer.setCompany(companyDao.find(resultSet
					.getInt(ID_COMPANY_FIELD)));
		}

		return computer;
	}

}
