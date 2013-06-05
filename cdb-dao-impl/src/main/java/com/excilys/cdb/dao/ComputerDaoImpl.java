package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.pojo.Computer;

@Repository
@Transactional
public class ComputerDaoImpl implements IComputerDao {

	private static final String SQL_INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private static final String SQL_FIND = "SELECT computer.id,computer.name,computer.introduced,computer.discontinued,company.id AS companyId,company.name AS companyName FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?";
	private static final String SQL_COUNT = "SELECT COUNT(*) FROM computer WHERE name LIKE ?";
	private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private static final String SQL_DELETE = "DELETE FROM computer where id = ?";
	private static final String SQL_LIST_PART1 = "SELECT computer.id,computer.name,computer.introduced,computer.discontinued,company.id AS companyId,company.name AS companyName FROM computer LEFT JOIN company ON computer.company_id = company.id";
	private static final String SQL_LIST_PART2 = " WHERE computer.name LIKE ? ORDER BY ISNULL(";
	private static final String SQL_LIST_PART3 = " LIMIT ? OFFSET ?";

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public int insert(Computer computer) throws DaoException {
		final Computer computerFinal = computer;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(SQL_INSERT,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, computerFinal.getName());
				ps.setDate(
						2,
						(computerFinal.getIntroduced() != null ? new java.sql.Date(
								computerFinal.getIntroduced().getTime()) : null));
				ps.setDate(
						3,
						(computerFinal.getDiscontinued() != null ? new java.sql.Date(
								computerFinal.getDiscontinued().getTime())
								: null));
				ps.setObject(4,
						computerFinal.getCompany() != null ? computerFinal
								.getCompany().getId() : null);
				return ps;
			}
		};
		jdbcTemplate.update(psc, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Transactional(readOnly=true)
	public Computer find(int id) throws DaoException {
		return jdbcTemplate.queryForObject(SQL_FIND, new Object[] { id },
				new ComputerRowMapper());
	}

	@Transactional(readOnly=true)
	public int numberOfComputers(String filterByName) throws DaoException {
		StringBuilder sb = new StringBuilder(filterByName.length() + 2);
		sb.append("%");
		sb.append(filterByName);
		sb.append("%");
		return jdbcTemplate.queryForObject(SQL_COUNT,
				new Object[] { sb.toString() }, Integer.class);
	}

	public void update(Computer computer) throws DaoException {
		jdbcTemplate.update(SQL_UPDATE,
				new Object[] {
						computer.getName(),
						computer.getIntroduced(),
						computer.getDiscontinued(),
						computer.getCompany() != null ? computer.getCompany()
								.getId() : null, computer.getId() });
	}

	public void delete(int id) throws DaoException {
		jdbcTemplate.update(SQL_DELETE, new Object[] { id });
	}

	@Transactional(readOnly=true)
	public List<Computer> list(String filterByName, String sortedColumn,
			String order, int limit, int offset) throws DaoException {
		String query = getOrderForQuery(sortedColumn, order);
		StringBuilder sb = new StringBuilder(filterByName.length() + 2);
		sb.append("%");
		sb.append(filterByName);
		sb.append("%");
		return jdbcTemplate.query(query, new Object[] { sb.toString(), limit,
				offset }, new ComputerRowMapper());
	}

	private static String getOrderForQuery(String columnName, String order) {
		StringBuilder sb = new StringBuilder();
		sb.append(SQL_LIST_PART1);

		if (columnName.equals(ICompanyDao.NAME_FIELD)) {
			sb.append(" LEFT JOIN company ON computer.company_id = company.id");
		}

		sb.append(SQL_LIST_PART2);
		sb.append(columnName);
		sb.append("),");
		sb.append(columnName);
		sb.append(" ");
		sb.append(order);
		sb.append(SQL_LIST_PART3);

		return sb.toString();
	}
}
