package com.excilys.cdb.dao;

import com.excilys.cdb.pojo.Company;

public interface ICompanyDao {

	String ID_FIELD = "company.id";
	String NAME_FIELD = "company.name";

	Company findById(int id);

}
