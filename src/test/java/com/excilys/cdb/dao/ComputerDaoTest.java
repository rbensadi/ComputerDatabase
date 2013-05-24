package com.excilys.cdb.dao;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.cdb.pojo.Computer;

public class ComputerDaoTest {

	private static IComputerDao computerDao;

	@BeforeClass
	public static void setUp() {
		System.out
				.println("--------------------------------------------------");
		System.out.println("COMPUTER DAO TEST");
		System.out
				.println("--------------------------------------------------");
		computerDao = new ComputerDaoImpl(DaoFactory.getInstance());
	}

	@AfterClass
	public static void tearDown() {
		computerDao = null;
	}

	@Test
	public void testFind() {
		System.out.println("[TestGet]");
		Computer computer = computerDao.findById(11);
		System.out.println("\t = " + computer);
	}

	@Test
	public void testList() {
		System.out.println("[TestList]");
		List<Computer> computers = computerDao.list();
		System.out.println("\tsize = " + computers.size());
		System.out.println("\tcomputer(11) = " + computers.get(11));
	}
}
