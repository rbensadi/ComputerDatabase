package com.excilys.cdb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.pojo.Company;

public class CompanyRowMapper implements RowMapper<Company> {

	public Company mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Company company = new Company();
		company.setId(resultSet.getInt(ICompanyDao.ID_FIELD));
		company.setName(resultSet.getString(ICompanyDao.NAME_FIELD));
		return company;
	}

}
