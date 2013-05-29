package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.pojo.Computer;
import com.excilys.cdb.pojo.Search;

public interface IComputerService {

	int LIMIT = 10;

	int insert(Computer computer);

	Computer findById(int id);

	int numberOfComputers(String filter);

	void update(Computer computer);

	void deleteById(int id);

	List<Computer> sortedByColumn(String filter, int columnId, int limit,
			int offset);

	Search getSearch(String filterByName, int sorted, int offset);
}
