package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.dao.CompanyDaoImpl;
import com.excilys.cdb.dao.DaoFactory;
import com.excilys.cdb.dao.ICompanyDao;
import com.excilys.cdb.pojo.Company;

public enum CompanyServiceImpl implements ICompanyService {

	INSTANCE;

	private DaoFactory daoFactory;
	private ICompanyDao companyDao;

	private CompanyServiceImpl() {
		daoFactory = DaoFactory.INSTANCE;
		companyDao = CompanyDaoImpl.INSTANCE;
	}

	public Company find(int id) {
		Company company = companyDao.find(id);
		daoFactory.closeConnection();
		return company;
	}

	public List<Company> list() {
		List<Company> companies = companyDao.list();
		daoFactory.closeConnection();
		return companies;
	}

}
