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
	private static final String ATT_TITLE = "title";

	private static final String TITLE_GREATER_THAN_1 = " computers found";
	private static final String TITLE_EQUALS_1 = "1 computer found";
	private static final String TITLE_NULL = " No computers found";

	private IComputerService computerService;

	@Override
	public void init() throws ServletException {
		computerService = ComputerServiceImpl.INSTANCE;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// FILTER PARAMETER
		String filterByName = (String) request.getParameter(ATT_FILTER_BY_NAME);
		if (filterByName == null) {
			filterByName = "";
		}

		int numberOfComputers = computerService.numberOfComputers(filterByName);

		int maxSheet = (int) Math.ceil(numberOfComputers
				/ (double) IComputerService.LIMIT);

		// PAGE PARAMETER
		int currentSheet = initIntegerFieldBetween(request, ATT_CURRENT_SHEET,
				1, 1, maxSheet);

		// OFFSET FOR SQL QUERY
		int offset;
		if (currentSheet == 1) {
			offset = 0;
		} else {
			offset = (currentSheet - 1) * IComputerService.LIMIT;
		}

		// SORTING PARAMETER
		int sorted = initIntegerFieldBetweenAbsolute(request,
				ATT_SORTED_COLUMN, 2, 2, 5);

		// LIST OF THE COMPUTERS
		List<Computer> computers = computerService.sortedByColumn(filterByName,
				sorted, IComputerService.LIMIT, offset);

		// INDICES OF THE PAGES
		int firstComputerIndice = (currentSheet - 1) * IComputerService.LIMIT;
		int lastComputerIndice = firstComputerIndice + IComputerService.LIMIT;

		if (lastComputerIndice > numberOfComputers) {
			lastComputerIndice = numberOfComputers;
		}

		// MESSAGE OF INSERT,UPDATE OR DELETE
		String message = (String) request.getSession().getAttribute(
				AddComputerController.ATT_MESSAGE);
		request.getSession().removeAttribute(AddComputerController.ATT_MESSAGE);

		// TITLE OF THE PAGE
		String title = numberOfComputers > 1 ? numberOfComputers
				+ TITLE_GREATER_THAN_1
				: numberOfComputers == 1 ? TITLE_EQUALS_1 : TITLE_NULL;

		// SET ATTRIBUTES TO THE REQUEST
		request.setAttribute(ATT_TITLE, title);
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
		if (value < min || value > max) {
			return true;
		}
		return false;
	}
}
