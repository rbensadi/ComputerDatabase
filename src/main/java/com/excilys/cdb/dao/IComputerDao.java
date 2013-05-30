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

	int insert(Computer computer);

	Computer find(int id);

	int numberOfComputers(String filter);

	void update(Computer computer);

	void delete(int id);

	List<Computer> list(String filterByName, String sortedColumn, String order,
			int limit, int offset);
}
