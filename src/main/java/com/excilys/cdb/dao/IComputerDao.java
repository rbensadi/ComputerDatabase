package com.excilys.cdb.dao;

import java.util.List;

import com.excilys.cdb.pojo.Computer;

public interface IComputerDao {

	String ID_FIELD = "id";
	String NAME_FIELD = "name";
	String INTRODUCED_FIELD = "introduced";
	String DISCOUNTINUED_FIELD = "discontinued";
	String ID_COMPANY_FIELD = "company_id";

	int insert(Computer computer);

	Computer findById(int id);

	Computer findByName(String name);

	int numberOfComputers(String filter);
	
	List<Computer> list();
	
	List<Computer> list(int limit, int offset);

	void update(Computer computer);

	void deleteById(int id);
	
	List<Computer> filterByName(String filter, int limit, int offset);
}
