package com.excilys.cdb.pojo;

import java.sql.Date;

public class Computer {

	private int id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Company company;

	public Computer() {
		// Do nothing...
	}

	public Computer(String name, Date introduced, Date disontinued,
			Company company) {
		setName(name);
		setIntroduced(introduced);
		setDiscontinued(disontinued);
		setCompany(company);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Computer@id:");
		sb.append(id);
		sb.append(",name:");
		sb.append(name);
		sb.append(",introduced:");
		sb.append(introduced);
		sb.append(",discountinued:");
		sb.append(discontinued);
		sb.append(",company:");
		sb.append(company);
		return sb.toString();
	}

}
