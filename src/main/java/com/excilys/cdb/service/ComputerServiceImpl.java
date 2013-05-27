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

	public Computer findByName(String name) {
		return computerDao.findByName(name);
	}

	public int numberOfComputers() {
		return computerDao.numberOfComputers();
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

}
