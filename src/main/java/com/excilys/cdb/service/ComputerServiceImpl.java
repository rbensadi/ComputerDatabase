package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.dao.ComputerDaoImpl;
import com.excilys.cdb.dao.DaoException;
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
		int id = 0;
		try {
			id = computerDao.insert(computer);
		} catch (DaoException e) {
			throw new ServiceException("ComputerService@insert() failed !", e);
		} finally {
			daoFactory.closeConnection();
		}
		return id;
	}

	public Computer find(int id) {
		Computer computer = null;
		try {
			computer = computerDao.find(id);
		} catch (DaoException e) {
			throw new ServiceException("ComputerService@find() failed !", e);
		} finally {
			daoFactory.closeConnection();
		}
		return computer;
	}

	public int numberOfComputers(String filterByName) {
		int numberOfComputers = 0;
		try {
			numberOfComputers = computerDao.numberOfComputers(filterByName);
		} catch (DaoException e) {
			throw new ServiceException(
					"ComputerService@numberOfComputers() failed !", e);
		} finally {
			daoFactory.closeConnection();
		}
		return numberOfComputers;
	}

	public void update(Computer computer) {
		try {
			computerDao.update(computer);
		} catch (DaoException e) {
			throw new ServiceException("ComputerService@update() failed !", e);
		} finally {
			daoFactory.closeConnection();
		}
	}

	public void delete(int id) {
		try {
			computerDao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException("ComputerService@delete() failed !", e);
		} finally {
			daoFactory.closeConnection();
		}
	}

	public List<Computer> list(String filterByName, int sortedColumn,
			int limit, int offset) {
		List<Computer> computers = null;
		try {
			computers = getComputers(filterByName, sortedColumn, limit, offset);
		} catch (DaoException e) {
			throw new ServiceException("ComputerService@list() failed !", e);
		} finally {
			daoFactory.closeConnection();
		}
		return computers;
	}

	private List<Computer> getComputers(String filterByName, int sortedColumn,
			int limit, int offset) throws DaoException {

		int absoluteColumnId = Math.abs(sortedColumn);
		String order = sortedColumn < 0 ? "DESC" : "ASC";

		return computerDao.list(filterByName,
				IComputerDao.COLUMS_NAME_ID[absoluteColumnId], order, limit,
				offset);
	}

	public ComputersAndCount getComputersAndCount(String filterByName,
			int sorted, int offset) {
		ComputersAndCount computersAndCount = new ComputersAndCount();
		try {
			computersAndCount.setNumberOfComputers(computerDao
					.numberOfComputers(filterByName));
			computersAndCount.setComputers(getComputers(filterByName, sorted,
					LIMIT, offset));
		} catch (DaoException e) {
			throw new ServiceException(
					"ComputerService@getComputersAndCount() failed !", e);
		} finally {
			daoFactory.closeConnection();
		}
		return computersAndCount;
	}

}
