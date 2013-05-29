package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.dao.ComputerDaoImpl;
import com.excilys.cdb.dao.DaoFactory;
import com.excilys.cdb.dao.IComputerDao;
import com.excilys.cdb.pojo.Computer;
import com.excilys.cdb.pojo.ComputersAndCount;

public enum ComputerServiceImpl implements IComputerService {

	INSTANCE;

	private DaoFactory daoFactory;
	private IComputerDao computerDao;

	private ComputerServiceImpl() {
		daoFactory = DaoFactory.INSTANCE;
		computerDao = ComputerDaoImpl.INSTANCE;
	}

	public int insert(Computer computer) {
		int id = computerDao.insert(computer);
		daoFactory.closeConnection();
		return id;
	}

	public Computer findById(int id) {
		Computer computer = computerDao.find(id);
		daoFactory.closeConnection();
		return computer;
	}

	public int numberOfComputers(String filterByName) {
		int numberOfComputers = computerDao.numberOfComputers(filterByName);
		daoFactory.closeConnection();
		return numberOfComputers;
	}

	public void update(Computer computer) {
		computerDao.update(computer);
		daoFactory.closeConnection();
	}

	public void delete(int id) {
		computerDao.delete(id);
		daoFactory.closeConnection();
	}

	public List<Computer> list(String filterByName, int sortedColumn,
			int limit, int offset) {

		List<Computer> computers = getComputers(filterByName, sortedColumn,
				limit, offset);
		
		daoFactory.closeConnection();

		return computers;
	}

	private List<Computer> getComputers(String filterByName, int sortedColumn,
			int limit, int offset) {

		int absoluteColumnId = Math.abs(sortedColumn);
		String order = sortedColumn < 0 ? "DESC" : "ASC";

		return computerDao.list(filterByName,
				IComputerDao.COLUMS_NAME_ID[absoluteColumnId], order, limit,
				offset);
	}

	public ComputersAndCount getComputersAndCount(String filterByName,
			int sorted, int offset) {
		ComputersAndCount computersAndCount = new ComputersAndCount();

		computersAndCount.setNumberOfComputers(computerDao
				.numberOfComputers(filterByName));
		computersAndCount.setComputers(getComputers(filterByName, sorted,
				LIMIT, offset));
		
		daoFactory.closeConnection();

		return computersAndCount;
	}

}
