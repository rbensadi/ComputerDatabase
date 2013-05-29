package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.dao.ComputerDaoImpl;
import com.excilys.cdb.dao.IComputerDao;
import com.excilys.cdb.pojo.Computer;
import com.excilys.cdb.pojo.Search;

public enum ComputerServiceImpl implements IComputerService {

	INSTANCE;

	private IComputerDao computerDao;

	private ComputerServiceImpl() {
		computerDao = ComputerDaoImpl.INSTANCE;
	}

	public int insert(Computer computer) {
		return computerDao.insert(computer);
	}

	public Computer findById(int id) {
		return computerDao.find(id);
	}

	public int numberOfComputers(String filter) {
		return computerDao.numberOfComputers(filter);
	}

	public void update(Computer computer) {
		computerDao.update(computer);
	}

	public void deleteById(int id) {
		computerDao.delete(id);
	}

	public List<Computer> list(String filterByName, int sortedColumn,
			int limit, int offset) {
		int absoluteColumnId = Math.abs(sortedColumn);
		String order = sortedColumn < 0 ? "DESC" : "ASC";

		return computerDao.list(filterByName,
				IComputerDao.COLUMS_NAME_ID[absoluteColumnId], order, limit,
				offset);
	}

	public Search getSearch(String filterByName, int sorted, int offset) {
		Search search = new Search();

		search.setNumberOfComputers(numberOfComputers(filterByName));
		search.setComputers(list(filterByName, sorted, LIMIT, offset));

		return search;
	}

}
