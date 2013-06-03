package com.excilys.cdb.dao;

import java.util.List;

import com.excilys.cdb.pojo.Computer;

public interface IComputerDao {

	String ID_FIELD = "computer.id";
	String NAME_FIELD = "computer.name";
	String INTRODUCED_FIELD = "computer.introduced";
	String DISCOUNTINUED_FIELD = "computer.discontinued";
	String ID_COMPANY_FIELD = "companyID";
	String NAME_COMPANY_FIELD = "companyName";

	String[] COLUMS_NAME_ID = { "", ID_FIELD, NAME_FIELD, INTRODUCED_FIELD,
			DISCOUNTINUED_FIELD, NAME_COMPANY_FIELD };

	int insert(Computer computer) throws DaoException;

	Computer find(int id) throws DaoException;

	int numberOfComputers(String filter) throws DaoException;

	void update(Computer computer) throws DaoException;

	void delete(int id) throws DaoException;

	List<Computer> list(String filterByName, String sortedColumn, String order,
			int limit, int offset) throws DaoException;
}
