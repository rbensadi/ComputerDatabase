package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.pojo.Computer;

public class ComputerDaoImpl implements IComputerDao {

	private static final String ID_FIELD = "id";
	private static final String NAME_FIELD = "name";
	private static final String INTRODUCED_FIELD = "introduced";
	private static final String DISCOUNTINUED_FIELD = "discontinued";
	private static final String ID_COMPANY_FIELD = "company_id";

	private DaoFactory daoFactory;

	public ComputerDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public Computer findById(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		return null;
	}

	public Computer findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Computer> list() {
		// TODO Auto-generated method stub
		return null;
	}

	public void insert(Computer computer) {
		// TODO Auto-generated method stub

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
