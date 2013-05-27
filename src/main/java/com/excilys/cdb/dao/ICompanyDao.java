package com.excilys.cdb.dao;

import com.excilys.cdb.pojo.Company;

public interface ICompanyDao {

	String ID_FIELD = "id";
	String NAME_FIELD = "name";

	Company findById(int id);

}
