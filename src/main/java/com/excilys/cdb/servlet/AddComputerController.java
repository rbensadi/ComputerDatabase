package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.form.AForm;
import com.excilys.cdb.form.CrudComputerForm;
import com.excilys.cdb.pojo.Company;
import com.excilys.cdb.pojo.Computer;
import com.excilys.cdb.service.CompanyServiceImpl;
import com.excilys.cdb.service.ComputerServiceImpl;
import com.excilys.cdb.service.ICompanyService;
import com.excilys.cdb.service.IComputerService;

@WebServlet("/computers/new")
public class AddComputerController extends HttpServlet {

	private static final long serialVersionUID = 1200000002L;

	public static final String ATT_COMPUTER = "computer";
	public static final String ATT_COMPANIES = "companies";
	public static final String ATT_SHOW_DELETE = "showDelete";
	public static final String ATT_MESSAGE = "message";

	public static final String MESSAGE_PART1 = "Computer ";
	private static final String MESSAGE_PART2 = " has been created";

	public static final String VIEW = "/WEB-INF/jsp/crudComputer.jsp";
	public static final String REDIRECT_VIEW = ".." + ComputersController.URL;

	private IComputerService computerService;
	private ICompanyService companyService;

	@Override
	public void init() throws ServletException {
		computerService = ComputerServiceImpl.INSTANCE;
		companyService = CompanyServiceImpl.INSTANCE;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Company> companies = companyService.list();

		request.setAttribute(ATT_COMPANIES, companies);
		request.setAttribute(ATT_SHOW_DELETE, true);

		getServletContext().getRequestDispatcher(VIEW).forward(request,
				response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		CrudComputerForm form = new CrudComputerForm();
		Computer computer = form.computerCrudValidation(request);

		if (form.isValid()) {
			computerService.insert(computer);
			StringBuilder sb = new StringBuilder();
			sb.append(MESSAGE_PART1);
			sb.append(computer.getName());
			sb.append(MESSAGE_PART2);
			request.getSession().setAttribute(ATT_MESSAGE, sb.toString());
			request.getSession().setAttribute(ATT_SHOW_DELETE, true);
			response.sendRedirect(REDIRECT_VIEW);
		} else {
			List<Company> companies = companyService.list();
			request.setAttribute(ATT_COMPANIES, companies);
			request.setAttribute(AForm.ATT_FORM, form);
			request.setAttribute(ATT_SHOW_DELETE, true);
			getServletContext().getRequestDispatcher(VIEW).forward(request,
					response);
		}
	}

}
