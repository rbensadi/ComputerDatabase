package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.pojo.Computer;
import com.excilys.cdb.service.ComputerServiceImpl;
import com.excilys.cdb.service.IComputerService;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String VIEW = "/WEB-INF/jsp/dashboard.jsp";

	private static final String ATT_CURRENT_SHEET = "currentSheet";
	private static final String ATT_FIRST_COMPUTER_INDICE = "firstComputerIndice";
	private static final String ATT_LAST_COMPUTER_INDICE = "lastComputerIndice";
	private static final String ATT_NUMBER_OF_COMPUTERS = "numberOfComputers";
	private static final String ATT_COMPUTERS = "computers";

	private IComputerService computerService;

	@Override
	public void init() throws ServletException {
		computerService = ComputerServiceImpl.INSTANCE;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int currentSheet;
		String currentSheetString = (String) request
				.getAttribute(ATT_CURRENT_SHEET);

		if (currentSheetString == null) {
			currentSheet = 1;
		} else {
			currentSheet = Integer.parseInt(currentSheetString);
		}

		int offset;
		if (currentSheet == 1) {
			offset = 0;
		} else {
			offset = (currentSheet - 1) * IComputerService.LIMIT;
		}

		int numberOfComputers = computerService.numberOfComputers();
		List<Computer> computers = computerService.list(IComputerService.LIMIT,
				offset);

		request.setAttribute(ATT_CURRENT_SHEET, currentSheet);
		request.setAttribute(ATT_FIRST_COMPUTER_INDICE, offset + 1);
		request.setAttribute(ATT_LAST_COMPUTER_INDICE, offset
				+ IComputerService.LIMIT);
		request.setAttribute(ATT_NUMBER_OF_COMPUTERS, numberOfComputers);
		request.setAttribute(ATT_COMPUTERS, computers);

		getServletContext().getRequestDispatcher(VIEW).forward(request,
				response);
	}

}
