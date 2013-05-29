package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.pojo.Company;

public interface ICompanyService {

	Company find(int id);
	
	List<Company> list();
	
}
