package com.excilys.cdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.DaoException;
import com.excilys.cdb.dao.DaoFactory;
import com.excilys.cdb.dao.ICompanyDao;
import com.excilys.cdb.pojo.Company;

@Service
public class CompanyServiceImpl implements ICompanyService {

	@Autowired
	private DaoFactory daoFactory;
	@Autowired
	private ICompanyDao companyDao;

	public Company find(int id) {
		Company company = null;
		try {
			company = companyDao.find(id);
		} catch (DaoException e) {
			throw new ServiceException("CompanyService@find() failed !", e);
		} finally {
			daoFactory.closeConnection();
		}
		return company;
	}

	public List<Company> list() {
		List<Company> companies = null;
		try {
			companies = companyDao.list();
		} catch (DaoException e) {
			throw new ServiceException("CompanyService@list() failed !", e);
		} finally {
			daoFactory.closeConnection();
		}
		return companies;
	}

}
