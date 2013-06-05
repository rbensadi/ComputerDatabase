package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.cdb.form.CrudComputerForm;
import com.excilys.cdb.form.FormUtils;
import com.excilys.cdb.service.IComputerService;

@WebServlet("/computers/delete")
public class DeleteComputerController extends HttpServlet {

	private static final long serialVersionUID = 1200000004L;

	private static final String MESSAGE = "Computer has been deleted";

	private ApplicationContext applicationContext;
	
	private IComputerService computerService;

	@Override
	public void init() throws ServletException {
		if (applicationContext == null) {
			applicationContext = WebApplicationContextUtils
					.getWebApplicationContext(getServletContext());
		}
		computerService = applicationContext.getBean(IComputerService.class);
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.parseInt(FormUtils.getFieldValue(request,
				CrudComputerForm.FIELD_ID));
		computerService.delete(id);
		request.getSession().setAttribute(AddComputerController.ATT_MESSAGE,
				MESSAGE);
		response.sendRedirect(AddComputerController.REDIRECT_VIEW);
	}

}
