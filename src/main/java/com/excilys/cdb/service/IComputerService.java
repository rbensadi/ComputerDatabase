package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.pojo.Computer;
import com.excilys.cdb.pojo.ComputersAndCount;

public interface IComputerService {

	int LIMIT = 10;

	int insert(Computer computer);

	Computer findById(int id);

	int numberOfComputers(String filterByName);

	void update(Computer computer);

	void delete(int id);

	List<Computer> list(String filterByName, int sortedColumn, int limit,
			int offset);

	ComputersAndCount getComputersAndCount(String filterByName, int sorted,
			int offset);
}
