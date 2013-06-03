package com.excilys.cdb.pojo;

public class Company {

	private int id;
	private String name;

	public Company() {
		// Do nothing...
	}

	public Company(int id, String name) {
		setId(id);
		setName(name);
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Company@id:");
		sb.append(id);
		sb.append(", name:");
		sb.append(name);
		return sb.toString();
	}

}
