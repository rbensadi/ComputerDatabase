package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.pojo.Company;
import com.excilys.cdb.pojo.Computer;

@Repository
public class ComputerDaoImpl implements IComputerDao {

	private static final String SQL_INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private static final String SQL_FIND = "SELECT computer.id,computer.name,computer.introduced,computer.discontinued,company.id AS companyId,company.name AS companyName FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?";
	private static final String SQL_COUNT = "SELECT COUNT(*) FROM computer WHERE name LIKE ?";
	private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private static final String SQL_DELETE = "DELETE FROM computer where id = ?";
	private static final String SQL_LIST_PART1 = "SELECT computer.id,computer.name,computer.introduced,computer.discontinued,company.id AS companyId,company.name AS companyName FROM computer LEFT JOIN company ON computer.company_id = company.id";
	private static final String SQL_LIST_PART2 = " WHERE computer.name LIKE ? ORDER BY ISNULL(";
	private static final String SQL_LIST_PART3 = " LIMIT ? OFFSET ?";

	@Autowired
	private DaoFactory daoFactory;

	public int insert(Computer computer) throws DaoException {
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

	public Computer find(int id) throws DaoException {
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
			throw new DaoException("ComputerDao@find() failed !", e);
		} finally {
			DaoUtils.silentClosing(preparedStatement, resultSet);
		}

		return computer;
	}

	public int numberOfComputers(String filterByName) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int count = 0;

		StringBuilder sb = new StringBuilder();
		sb.append("%");
		sb.append(filterByName);
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
			throw new DaoException("ComputerDao@numberOfComputers() failed !", e);
		} finally {
			DaoUtils.silentClosing(preparedStatement, resultSet);
		}

		return count;
	}

	public void update(Computer computer) throws DaoException {
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

	public void delete(int id) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		connection = daoFactory.getConnection();
		try {
			preparedStatement = DaoUtils.getPreparedStatement(connection,
					SQL_DELETE, false, id);
			int result = preparedStatement.executeUpdate();
			if (result != 1) {
				throw new DaoException("ComputerDao@delete() failed !");
			}
		} catch (SQLException e) {
			throw new DaoException("ComputerDao@delete() failed !", e);
		} finally {
			DaoUtils.silentClosing(preparedStatement);
		}
	}

	public List<Computer> list(String filterByName, String sortedColumn,
			String order, int limit, int offset) throws DaoException {
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
			throw new DaoException("ComputerDao@list() failed !", e);
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
			Company company = new Company();
			company.setId(resultSet.getInt(ID_COMPANY_FIELD));
			company.setName(resultSet.getString(NAME_COMPANY_FIELD));
			computer.setCompany(company);
		}

		return computer;
	}

}
