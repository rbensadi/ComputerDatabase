package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.pojo.Computer;

public enum ComputerDaoImpl implements IComputerDao {

	INSTANCE;

	private static final String ID_FIELD = "id";
	private static final String NAME_FIELD = "name";
	private static final String INTRODUCED_FIELD = "introduced";
	private static final String DISCOUNTINUED_FIELD = "discontinued";
	private static final String ID_COMPANY_FIELD = "company_id";

	private static final String SQL_FIND_BY_ID = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id = ?";

	private static final String SQL_INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";

	private DaoFactory daoFactory;

	private ComputerDaoImpl() {
		this.daoFactory = DaoFactory.INSTANCE;
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
					computer.getCompanyId());
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		} finally {
			DaoUtils.silentClosing(connection, preparedStatement, resultSet);
		}

		return computer;
	}

	public Computer findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Computer> list() {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(Computer computer) {
		// TODO Auto-generated method stub

	}

	public void deleteById(int id) {
		// TODO Auto-generated method stub

	}

	public Computer map(ResultSet resultSet) throws SQLException {
		Computer computer = new Computer();

		computer.setId(resultSet.getInt(ID_FIELD));
		computer.setName(resultSet.getString(NAME_FIELD));
		computer.setIntroduced(resultSet.getDate(INTRODUCED_FIELD));
		computer.setDiscontinued(resultSet.getDate(DISCOUNTINUED_FIELD));
		computer.setCompanyId(resultSet.getInt(ID_COMPANY_FIELD));

		return computer;
	}
}
