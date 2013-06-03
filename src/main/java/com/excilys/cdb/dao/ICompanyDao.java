package com.excilys.cdb.dao;

import java.util.List;

import com.excilys.cdb.pojo.Company;

public interface ICompanyDao {

	String ID_FIELD = "company.id";
	String NAME_FIELD = "company.name";

	Company find(int id) throws DaoException;

	List<Company> list() throws DaoException;
}
