package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.cdb.form.AForm;
import com.excilys.cdb.form.CrudComputerForm;
import com.excilys.cdb.form.FormUtils;
import com.excilys.cdb.pojo.Company;
import com.excilys.cdb.pojo.Computer;
import com.excilys.cdb.service.ICompanyService;
import com.excilys.cdb.service.IComputerService;

@WebServlet("/computers/edit")
public class UpdateComputerController extends HttpServlet {

	private static final long serialVersionUID = 1200000003L;

	private static final String MESSAGE_PART2 = " has been updated";

	private ApplicationContext applicationContext;

	private IComputerService computerService;
	private ICompanyService companyService;

	@Override
	public void init() throws ServletException {
		if (applicationContext == null) {
			applicationContext = WebApplicationContextUtils
					.getWebApplicationContext(getServletContext());
		}
		computerService = applicationContext.getBean(IComputerService.class);
		companyService = applicationContext.getBean(ICompanyService.class);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String idStr = FormUtils.getFieldValue(request,
				CrudComputerForm.FIELD_ID);
		Integer id;
		Computer computer;
		try {
			id = Integer.parseInt(idStr);
			computer = computerService.find(id);
		} catch (NumberFormatException e) {
			computer = null;
		}

		CrudComputerForm form = new CrudComputerForm();
		form.setComputer(computer);

		if (computer != null) {
			List<Company> companies = companyService.list();
			request.setAttribute(AddComputerController.ATT_COMPUTER, computer);
			request.setAttribute(AForm.ATT_FORM, form);
			request.setAttribute(AddComputerController.ATT_COMPANIES, companies);
			request.setAttribute(AddComputerController.ATT_SHOW_DELETE, false);
			getServletContext()
					.getRequestDispatcher(AddComputerController.VIEW).forward(
							request, response);
		} else {
			response.sendRedirect(AddComputerController.REDIRECT_VIEW);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		CrudComputerForm form = new CrudComputerForm();
		Computer computer = form.computerCrudValidationUpdate(request);

		if (form.isValid()) {
			computerService.update(computer);
			StringBuilder sb = new StringBuilder();
			sb.append(AddComputerController.MESSAGE_PART1);
			sb.append(computer.getName());
			sb.append(MESSAGE_PART2);
			request.getSession().setAttribute(
					AddComputerController.ATT_MESSAGE, sb.toString());
			request.getSession().setAttribute(
					AddComputerController.ATT_SHOW_DELETE, false);
			response.sendRedirect(AddComputerController.REDIRECT_VIEW);
		} else {
			List<Company> companies = companyService.list();
			request.setAttribute(AForm.ATT_FORM, form);
			request.setAttribute(AddComputerController.ATT_COMPUTER, computer);
			request.setAttribute(AddComputerController.ATT_COMPANIES, companies);
			request.setAttribute(AddComputerController.ATT_SHOW_DELETE, false);
			getServletContext()
					.getRequestDispatcher(AddComputerController.VIEW).forward(
							request, response);
		}
	}

}
