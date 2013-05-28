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

@WebServlet("/computers/edit")
public class UpdateDeleteComputerController extends HttpServlet {

	private static final long serialVersionUID = 1200000003L;

	private IComputerService computerService;
	private ICompanyService companyService;

	@Override
	public void init() throws ServletException {
		computerService = ComputerServiceImpl.INSTANCE;
		companyService = CompanyServiceImpl.INSTANCE;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		CrudComputerForm form = new CrudComputerForm();
		Computer computer = form.getComputer(request);

		if (computer != null) {
			List<Company> companies = companyService.list();
			request.setAttribute(AddComputerController.ATT_COMPUTER, computer);
			request.setAttribute(AForm.ATT_FORM, form);
			request.setAttribute(AddComputerController.ATT_COMPANIES, companies);
			request.setAttribute(AddComputerController.ATT_IS_SERVLET_ADD,
					false);
			getServletContext()
					.getRequestDispatcher(AddComputerController.VIEW).forward(
							request, response);
		} else {
			response.sendRedirect(AddComputerController.REDIRECT_VIEW);
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		CrudComputerForm form = new CrudComputerForm();
		Computer computer = form.computerCrudValidationUpdate(request);

		if (form.isValid()) {
			computerService.update(computer);
			request.getSession().setAttribute(
					AddComputerController.ATT_COMPUTER, computer);
			request.getSession().setAttribute(
					AddComputerController.ATT_IS_SERVLET_ADD, false);
			response.sendRedirect(AddComputerController.REDIRECT_VIEW);
		} else {
			List<Company> companies = companyService.list();
			request.setAttribute(AForm.ATT_FORM, form);
			request.setAttribute(AddComputerController.ATT_COMPUTER, computer);
			request.setAttribute(AddComputerController.ATT_COMPANIES, companies);
			request.setAttribute(AddComputerController.ATT_IS_SERVLET_ADD,
					false);
			getServletContext()
					.getRequestDispatcher(AddComputerController.VIEW).forward(
							request, response);
		}
	}

}
