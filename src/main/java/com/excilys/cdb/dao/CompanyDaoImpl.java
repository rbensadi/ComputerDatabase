package com.excilys.cdb.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.pojo.Company;

@Repository
public class CompanyDaoImpl implements ICompanyDao {

	private static final String SQL_FIND = "SELECT id,name FROM company WHERE id = ?";
	private static final String SQL_LIST = "SELECT id,name FROM company ORDER BY name";

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Company find(int id) throws DaoException {
		return jdbcTemplate.queryForObject(SQL_FIND, new Object[] { id },
				new CompanyRowMapper());
	}

	public List<Company> list() throws DaoException {
		return jdbcTemplate.query(SQL_LIST, new CompanyRowMapper());
	}
}
