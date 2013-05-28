package com.excilys.cdb.form;

import javax.servlet.http.HttpServletRequest;

public class FormUtils {

	public static String getFieldValue(HttpServletRequest request, String field) {
		String value = request.getParameter(field);
		if (value == null || value.trim().length() == 0) {
			return null;
		} else {
			return value;
		}
	}

}
