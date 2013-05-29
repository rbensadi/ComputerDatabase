package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.form.FormUtils;
import com.excilys.cdb.pojo.Search;
import com.excilys.cdb.service.ComputerServiceImpl;
import com.excilys.cdb.service.IComputerService;

@WebServlet("/computers")
public class ComputersController extends HttpServlet {

	private static final long serialVersionUID = 1200000001L;

	public static final String VIEW = "/WEB-INF/jsp/computers.jsp";
	public static final String URL = "/computers";

	private static final String ATT_CURRENT_PAGE = "p";
	private static final String ATT_FILTER_BY_NAME = "f";
	private static final String ATT_SORTED_COLUMN = "s";
	private static final String ATT_MAX_NUMBER_OF_PAGES = "maxNumberOfPages";
	private static final String ATT_FIRST_COMPUTER_INDICE = "firstComputerIndice";
	private static final String ATT_LAST_COMPUTER_INDICE = "lastComputerIndice";
	private static final String ATT_NUMBER_OF_COMPUTERS = "numberOfComputers";
	private static final String ATT_COMPUTERS = "computers";
	private static final String ATT_TITLE = "title";

	private static final String TITLE_GREATER_THAN_1 = " computers found";
	private static final String TITLE_EQUALS_1 = "1 computer found";
	private static final String TITLE_NULL = " No computers found";

	private static final int DEFAULT_CURRENT_PAGE = 1;
	private static final String DEFAULT_FILTER_BY_NAME = "";
	private static final int DEFAULT_SORTED = 2;
	private static final int SORTED_MIN = DEFAULT_SORTED;
	private static final int SORTED_MAX = 5;

	private IComputerService computerService;

	@Override
	public void init() throws ServletException {
		computerService = ComputerServiceImpl.INSTANCE;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// CURRENT PAGE
		String currentPageStr = FormUtils.getFieldValue(request,
				ATT_CURRENT_PAGE);
		int currentPage = initIntegerField(currentPageStr, DEFAULT_CURRENT_PAGE);
		if (currentPage < DEFAULT_CURRENT_PAGE) {
			currentPage = DEFAULT_CURRENT_PAGE;
		}

		// FILTER BY NAME
		String filterByName = FormUtils.getFieldValue(request,
				ATT_FILTER_BY_NAME);
		if (filterByName == null) {
			filterByName = DEFAULT_FILTER_BY_NAME;
		}

		// SORTING
		String sortedColumnStr = FormUtils.getFieldValue(request, ATT_SORTED_COLUMN);
		int sortedColumn = initIntegerField(sortedColumnStr, DEFAULT_SORTED);
		int sortedAbsolute = Math.abs(sortedColumn);
		if (sortedAbsolute < SORTED_MIN || sortedAbsolute > SORTED_MAX) {
			sortedColumn = DEFAULT_SORTED;
		}

		// OFFSET
		int offset;
		if (currentPage == DEFAULT_CURRENT_PAGE) {
			offset = 0;
		} else {
			offset = (currentPage - 1) * IComputerService.LIMIT;
		}

		// CALL THE SERVICE
		Search search = computerService.getSearch(filterByName, sortedColumn, offset);

		int maxNumberOfPages = (int) Math.ceil(search.getNumberOfComputers()
				/ (double) IComputerService.LIMIT);

		// INDICES OF THE PAGES
		int firstComputerIndice = (currentPage - 1) * IComputerService.LIMIT;
		int lastComputerIndice = firstComputerIndice + IComputerService.LIMIT;

		if (lastComputerIndice > search.getNumberOfComputers()) {
			lastComputerIndice = search.getNumberOfComputers();
		}

		// MESSAGE OF INSERT,UPDATE OR DELETE
		String message = (String) request.getSession().getAttribute(
				AddComputerController.ATT_MESSAGE);
		request.getSession().removeAttribute(AddComputerController.ATT_MESSAGE);

		// TITLE OF THE PAGE
		String title = search.getNumberOfComputers() > 1 ? search
				.getNumberOfComputers() + TITLE_GREATER_THAN_1 : search
				.getNumberOfComputers() == 1 ? TITLE_EQUALS_1 : TITLE_NULL;

		// SET ATTRIBUTES TO THE REQUEST
		request.setAttribute(ATT_TITLE, title);
		request.setAttribute(AddComputerController.ATT_MESSAGE, message);
		request.setAttribute(ATT_CURRENT_PAGE, currentPage);
		request.setAttribute(ATT_FILTER_BY_NAME, filterByName);
		request.setAttribute(ATT_SORTED_COLUMN, sortedColumn);
		request.setAttribute(ATT_MAX_NUMBER_OF_PAGES, maxNumberOfPages);
		request.setAttribute(ATT_FIRST_COMPUTER_INDICE, firstComputerIndice + 1);
		request.setAttribute(ATT_LAST_COMPUTER_INDICE, lastComputerIndice);
		request.setAttribute(ATT_NUMBER_OF_COMPUTERS,
				search.getNumberOfComputers());
		request.setAttribute(ATT_COMPUTERS, search.getComputers());

		getServletContext().getRequestDispatcher(VIEW).forward(request,
				response);
	}

	private static int initIntegerField(String field, int defaultValue) {
		Integer value;
		try {
			value = Integer.parseInt(field);
		} catch (NumberFormatException e) {
			value = defaultValue;
		}
		return value;
	}
}
