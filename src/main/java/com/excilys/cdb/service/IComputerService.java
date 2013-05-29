package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.pojo.Computer;

public interface IComputerService {

	int LIMIT = 10;

	int insert(Computer computer);

	Computer findById(int id);

	int numberOfComputers(String filter);

	List<Computer> list();

	List<Computer> list(int limit, int offset);

	void update(Computer computer);

	void deleteById(int id);

	List<Computer> filterByName(String filter, int limit, int offset);

	List<Computer> sortedByColumn(String filter, int columnId, int limit,
			int offset);
	
}
