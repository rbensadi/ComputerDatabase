package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.form.AForm;
import com.excilys.cdb.form.AddComputerForm;
import com.excilys.cdb.pojo.Company;
import com.excilys.cdb.pojo.Computer;
import com.excilys.cdb.service.CompanyServiceImpl;
import com.excilys.cdb.service.ICompanyService;

@WebServlet("/computers/new")
public class AddComputerController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String ATT_COMPUTER = "computer";
	private static final String ATT_COMPANIES = "companies";

	private static final String VIEW = "/WEB-INF/jsp/addComputer.jsp";

	private ICompanyService companyService;

	@Override
	public void init() throws ServletException {
		companyService = CompanyServiceImpl.INSTANCE;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Company> companies = companyService.list();

		request.setAttribute(ATT_COMPANIES, companies);

		getServletContext().getRequestDispatcher(VIEW).forward(request,
				response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		AddComputerForm form = new AddComputerForm();

		Computer computer = form.addComputer(request);

		List<Company> companies = companyService.list();

		request.setAttribute(ATT_COMPANIES, companies);
		request.setAttribute(ATT_COMPUTER, computer);
		request.setAttribute(AForm.ATT_FORM, form);

		getServletContext().getRequestDispatcher(VIEW).forward(request,
				response);
	}

}
