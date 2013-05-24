package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dao.DaoFactory;
import com.excilys.cdb.dao.IComputerDao;
import com.excilys.cdb.dao.InitializationDaoFactory;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private IComputerDao computerDao;

	@Override
	public void init() throws ServletException {
		computerDao = ((DaoFactory) getServletContext().getAttribute(
				InitializationDaoFactory.ATT_DAO_FACTORY)).getComputerDao();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
