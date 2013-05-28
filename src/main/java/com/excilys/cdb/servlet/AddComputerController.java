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
import com.excilys.cdb.service.ComputerServiceImpl;
import com.excilys.cdb.service.ICompanyService;
import com.excilys.cdb.service.IComputerService;

@WebServlet("/computers/new")
public class AddComputerController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final String ATT_COMPUTER = "computer";
	private static final String ATT_COMPANIES = "companies";

	public static final String VIEW = "/WEB-INF/jsp/crudComputer.jsp";
	private static final String REDIRECT_VIEW = ".."
			+ ComputersController.URL;

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

		getServletContext().getRequestDispatcher(VIEW).forward(request,
				response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		AddComputerForm form = new AddComputerForm();
		Computer computer = form.addComputer(request);

		if (form.isValid()) {
			int id = computerService.insert(computer);
			computer.setId(id);
			request.getSession().setAttribute(ATT_COMPUTER, computer);
			response.sendRedirect(REDIRECT_VIEW);
		} else {
			List<Company> companies = companyService.list();
			request.setAttribute(ATT_COMPANIES, companies);
			request.setAttribute(AForm.ATT_FORM, form);
			getServletContext().getRequestDispatcher(VIEW).forward(request,
					response);
		}
	}

}
