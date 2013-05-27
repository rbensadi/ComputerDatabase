package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.dao.ComputerDaoImpl;
import com.excilys.cdb.dao.IComputerDao;
import com.excilys.cdb.pojo.Computer;

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
		return computerDao.findById(id);
	}

	public int numberOfComputers(String filter) {
		return computerDao.numberOfComputers(filter);
	}

	public List<Computer> list() {
		return computerDao.list();
	}

	public List<Computer> list(int limit, int offset) {
		return computerDao.list(limit, offset);
	}

	public void update(Computer computer) {
		computerDao.update(computer);
	}

	public void deleteById(int id) {
		computerDao.deleteById(id);
	}

	public List<Computer> filterByName(String filter, int limit, int offset) {
		return computerDao.filterByName(filter, limit, offset);
	}

	public List<Computer> sortedByColumn(String filter, int columnId,
			int limit, int offset) {
		int absoluteColumnId = Math.abs(columnId);
		String order = columnId < 0 ? "DESC" : "ASC";

		return computerDao.sortedByColumn(filter,
				IComputerDao.COLUMS_NAME_ID[absoluteColumnId], order, limit,
				offset);
	}
}
