package com.excilys.cdb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.pojo.Company;
import com.excilys.cdb.pojo.Computer;

public class ComputerRowMapper implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Computer computer = new Computer();
		computer.setId(resultSet.getInt(IComputerDao.ID_FIELD));
		computer.setName(resultSet.getString(IComputerDao.NAME_FIELD));
		computer.setIntroduced(resultSet.getDate(IComputerDao.INTRODUCED_FIELD));
		computer.setDiscontinued(resultSet
				.getDate(IComputerDao.DISCOUNTINUED_FIELD));
		Object o = resultSet.getObject(IComputerDao.ID_COMPANY_FIELD);
		if (o == null) {
			computer.setCompany(null);
		} else {
			Company company = new Company();
			company.setId(resultSet.getInt(IComputerDao.ID_COMPANY_FIELD));
			company.setName(resultSet
					.getString(IComputerDao.NAME_COMPANY_FIELD));
			computer.setCompany(company);
		}
		return computer;
	}
}
