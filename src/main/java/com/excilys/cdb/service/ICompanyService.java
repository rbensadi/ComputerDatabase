package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.pojo.Company;

public interface ICompanyService {

	Company findById(int id);
	
	List<Company> list();
	
}
