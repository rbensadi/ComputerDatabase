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

@WebServlet("/computers")
public class ComputersController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String VIEW = "/WEB-INF/jsp/computers.jsp";

	private static final String ATT_CURRENT_SHEET = "p";
	private static final String ATT_FILTER_BY_NAME = "f";
	private static final String ATT_MAX_SHEET = "maxSheet";
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

		// CurrentSheet
		int currentSheet;
		String currentSheetString = (String) request
				.getParameter(ATT_CURRENT_SHEET);

		if (currentSheetString == null) {
			currentSheet = 1;
		} else {
			try {
				currentSheet = Integer.parseInt(currentSheetString);
			} catch (NumberFormatException nfe) {
				currentSheet = 1;
			}
			if (currentSheet <= 0) {
				currentSheet = 1;
			}
		}

		String filterByName = (String) request.getParameter(ATT_FILTER_BY_NAME);

		// Count of the offset for the computers list
		int offset;
		if (currentSheet == 1) {
			offset = 0;
		} else {
			offset = (currentSheet - 1) * IComputerService.LIMIT;
		}

		// Getting the good list of computers
		int numberOfComputers;
		List<Computer> computers;
		if (filterByName != null) {
			computers = computerService.filterByName(filterByName,
					IComputerService.LIMIT, offset);
			numberOfComputers = computerService.numberOfComputers(filterByName);
		} else {
			computers = computerService.list(IComputerService.LIMIT, offset);
			numberOfComputers = computerService.numberOfComputers("");
		}

		int maxSheet = (int) Math.ceil(numberOfComputers
				/ (double) IComputerService.LIMIT);

		String toto;
		// Set attributes to the request
		request.setAttribute(ATT_FILTER_BY_NAME, filterByName);
		request.setAttribute(ATT_CURRENT_SHEET, currentSheet);
		request.setAttribute(ATT_MAX_SHEET, maxSheet);
		request.setAttribute(ATT_FIRST_COMPUTER_INDICE, offset + 1);
		request.setAttribute(ATT_LAST_COMPUTER_INDICE, offset
				+ IComputerService.LIMIT);
		request.setAttribute(ATT_NUMBER_OF_COMPUTERS, numberOfComputers);
		request.setAttribute(ATT_COMPUTERS, computers);

		getServletContext().getRequestDispatcher(VIEW).forward(request,
				response);
	}

}
