package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dao.ComputerDaoImpl;
import com.excilys.cdb.dao.IComputerDao;
import com.excilys.cdb.pojo.Computer;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String VIEW = "/WEB-INF/jsp/dashboard.jsp";

	private IComputerDao computerDao;

	@Override
	public void init() throws ServletException {
		computerDao = ComputerDaoImpl.INSTANCE;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Computer computer = computerDao.findByName("mehdi");
		System.out.println(computer.getName());

		getServletContext().getRequestDispatcher(VIEW).forward(request,
				response);
	}

}
