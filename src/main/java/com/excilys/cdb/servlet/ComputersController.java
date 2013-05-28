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

	private static final long serialVersionUID = 1200000001L;

	public static final String VIEW = "/WEB-INF/jsp/computers.jsp";
	public static final String URL = "/computers";

	private static final String ATT_CURRENT_SHEET = "p";
	private static final String ATT_FILTER_BY_NAME = "f";
	private static final String ATT_SORTED_COLUMN = "s";
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

		String filterByName = (String) request.getParameter(ATT_FILTER_BY_NAME);
		if (filterByName == null) {
			filterByName = "";
		}
		int numberOfComputers = computerService.numberOfComputers(filterByName);
		int maxSheet = (int) Math.ceil(numberOfComputers
				/ (double) IComputerService.LIMIT);

		int currentSheet = initIntegerFieldBetween(request, ATT_CURRENT_SHEET,
				1, 1, maxSheet + 1);

		// Count of the offset for the computers list
		int offset;
		if (currentSheet == 1) {
			offset = 0;
		} else {
			offset = (currentSheet - 1) * IComputerService.LIMIT;
		}

		// Set the value of the sorted column
		// String sortedString = (String)
		// request.getParameter(ATT_SORTED_COLUMN);
		// int sorted;
		//
		// if (sortedString == null) {
		// sorted = 2;
		// } else {
		// try {
		// sorted = Integer.parseInt(sortedString);
		// } catch (NumberFormatException nfe) {
		// sorted = 2;
		// }
		// }
		int sorted = initIntegerFieldBetweenAbsolute(request,
				ATT_SORTED_COLUMN, 2, 2, 5);

		// Getting the good list of computers
		List<Computer> computers = computerService.sortedByColumn(filterByName,
				sorted, IComputerService.LIMIT, offset);

		int firstComputerIndice = (currentSheet - 1) * IComputerService.LIMIT;
		int lastComputerIndice = firstComputerIndice + IComputerService.LIMIT;

		if (lastComputerIndice > numberOfComputers) {
			lastComputerIndice = numberOfComputers;
		}

		// Get the computer if added in the session and delete it
		String message = (String) request.getSession().getAttribute(
				AddComputerController.ATT_MESSAGE);
		request.getSession().removeAttribute(AddComputerController.ATT_MESSAGE);

		// Set attributes to the request
		request.setAttribute(AddComputerController.ATT_MESSAGE, message);
		request.setAttribute(ATT_CURRENT_SHEET, currentSheet);
		request.setAttribute(ATT_FILTER_BY_NAME, filterByName);
		request.setAttribute(ATT_SORTED_COLUMN, sorted);
		request.setAttribute(ATT_MAX_SHEET, maxSheet);
		request.setAttribute(ATT_FIRST_COMPUTER_INDICE, firstComputerIndice + 1);
		request.setAttribute(ATT_LAST_COMPUTER_INDICE, lastComputerIndice);
		request.setAttribute(ATT_NUMBER_OF_COMPUTERS, numberOfComputers);
		request.setAttribute(ATT_COMPUTERS, computers);

		getServletContext().getRequestDispatcher(VIEW).forward(request,
				response);
	}

	private static int initIntegerField(HttpServletRequest request,
			String field, int defaultValue) {
		String valueStr = request.getParameter(field);
		Integer valueInt;
		try {
			valueInt = Integer.parseInt(valueStr);
		} catch (NumberFormatException e) {
			valueInt = defaultValue;
		}
		return valueInt;
	}

	private static int initIntegerFieldBetween(HttpServletRequest request,
			String field, int defaultValue, int min, int max) {
		Integer valueInt = initIntegerField(request, field, defaultValue);
		if (isBetween(valueInt, min, max)) {
			valueInt = defaultValue;
		}
		return valueInt;
	}

	private static int initIntegerFieldBetweenAbsolute(
			HttpServletRequest request, String field, int defaultValue,
			int min, int max) {
		Integer valueInt = initIntegerField(request, field, defaultValue);
		if (isBetween(Math.abs(valueInt), min, max)) {
			valueInt = defaultValue;
		}
		return valueInt;
	}

	private static boolean isBetween(int value, int min, int max) {
		if (value <= min || value >= max) {
			return true;
		}
		return false;
	}
}
