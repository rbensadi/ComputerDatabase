package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.dao.CompanyDaoImpl;
import com.excilys.cdb.dao.ICompanyDao;
import com.excilys.cdb.pojo.Company;

public enum CompanyServiceImpl implements ICompanyService {

	INSTANCE;

	private ICompanyDao companyDao;

	private CompanyServiceImpl() {
		companyDao = CompanyDaoImpl.INSTANCE;
	}

	public Company findById(int id) {
		return companyDao.findById(id);
	}

	public List<Company> list() {
		return companyDao.list();
	}

}
