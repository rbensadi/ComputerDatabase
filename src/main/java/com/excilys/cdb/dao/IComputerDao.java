package com.excilys.cdb.dao;

import java.util.List;

import com.excilys.cdb.pojo.Computer;

public interface IComputerDao {

	int insert(Computer computer);
	
	Computer findById(int id);

	Computer findByName(String name);

	List<Computer> list();

	void update(Computer computer);
	
	void deleteById(int id);
}
