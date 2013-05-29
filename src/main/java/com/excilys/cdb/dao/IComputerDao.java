package com.excilys.cdb.dao;

import java.util.List;

import com.excilys.cdb.pojo.Computer;

public interface IComputerDao {

	String ID_FIELD = "computer.id";
	String NAME_FIELD = "computer.name";
	String INTRODUCED_FIELD = "computer.introduced";
	String DISCOUNTINUED_FIELD = "computer.discontinued";
	String ID_COMPANY_FIELD = "computer.company_id";

	String[] COLUMS_NAME_ID = { "", ID_FIELD, NAME_FIELD, INTRODUCED_FIELD,
			DISCOUNTINUED_FIELD, ICompanyDao.NAME_FIELD };

	int insert(Computer computer);

	Computer findById(int id);

	int numberOfComputers(String filter);

	void update(Computer computer);

	void deleteById(int id);


	List<Computer> sortedByColumn(String filter, String columnName, String order, int limit,
			int offset);
}
