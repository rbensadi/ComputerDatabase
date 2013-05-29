package com.excilys.cdb.pojo;

import java.util.List;

public class Search {

	private int numberOfComputers;
	private List<Computer> computers;

	public int getNumberOfComputers() {
		return numberOfComputers;
	}

	public void setNumberOfComputers(int numberOfComputers) {
		this.numberOfComputers = numberOfComputers;
	}

	public List<Computer> getComputers() {
		return computers;
	}

	public void setComputers(List<Computer> computers) {
		this.computers = computers;
	}
}
