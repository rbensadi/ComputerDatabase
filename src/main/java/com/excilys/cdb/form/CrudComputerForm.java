package com.excilys.cdb.form;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.excilys.cdb.pojo.Company;
import com.excilys.cdb.pojo.Computer;
import com.excilys.cdb.pojo.FormElement;

public class CrudComputerForm extends AForm {

	private static final String INTRODUCED_ERROR_MESSAGE = "AddComputerForm@introducedValidation() : The introduced date has a not the 'yyyy-MM-dd' format.";
	private static final String DISCONTINUED_ERROR_MESSAGE = "AddComputerForm@discontinuedValidation() : The discontinued date has a not the 'yyyy-MM-dd' format.";

	public static final String FIELD_ID = "id";
	private static final String FIELD_NAME = "name";
	private static final String FIELD_INTRODUCED = "introduced";
	private static final String FIELD_DISCONTINUED = "discontinued";
	private static final String FIELD_COMPANY = "company";

	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd",
			Locale.FRANCE);

	static {
		df.setLenient(false);
	}

	public CrudComputerForm() {
	}

	public Computer computerCrudValidation(HttpServletRequest request) {
		String name = FormUtils.getFieldValue(request, FIELD_NAME);
		String introduced = FormUtils.getFieldValue(request, FIELD_INTRODUCED);
		String discontinued = FormUtils.getFieldValue(request,
				FIELD_DISCONTINUED);
		String company = FormUtils.getFieldValue(request, FIELD_COMPANY);

		Computer computer = new Computer();

		nameProcess(computer, name);
		introducedProcess(computer, introduced);
		discontinuedProcess(computer, discontinued);
		companyProcess(computer, company);

		if (computer.getIntroduced() != null
				&& computer.getDiscontinued() != null) {
			if (computer.getIntroduced().compareTo(computer.getDiscontinued()) > 0) {
				fields.put(FIELD_DISCONTINUED, new FormElement(discontinued,
						false));
			}
		}

		return computer;
	}

	public Computer computerCrudValidationUpdate(HttpServletRequest request) {
		Computer computer = computerCrudValidation(request);
		Integer id = Integer.parseInt(FormUtils
				.getFieldValue(request, FIELD_ID));
		computer.setId(id);
		return computer;
	}

	public void setComputer(Computer computer) {
		if (computer == null) {
			return;
		}

		putFormElement(FIELD_NAME, computer.getName(), true);

		if (computer.getIntroduced() != null) {
			putFormElement(FIELD_INTRODUCED,
					df.format(computer.getIntroduced()), true);
		} else {
			putFormElement(FIELD_INTRODUCED, null, true);
		}

		if (computer.getDiscontinued() != null) {
			putFormElement(FIELD_DISCONTINUED,
					df.format(computer.getDiscontinued()), true);
		} else {
			putFormElement(FIELD_DISCONTINUED, null, true);
		}

		if (computer.getCompany() != null) {
			putFormElement(FIELD_COMPANY,
					String.valueOf(computer.getCompany().getId()), true);
		} else {
			putFormElement(FIELD_COMPANY, null, true);
		}
	}

	private void putFormElement(String field, String value, boolean valid) {
		fields.put(field, new FormElement(value, valid));
	}

	private void nameProcess(Computer computer, String name) {
		try {
			nameValidation(name);
			computer.setName(name);
			fields.put(FIELD_NAME, new FormElement(name, true));
		} catch (FormValidationException e) {
			fields.put(FIELD_NAME, new FormElement(name, false));
		}
	}

	private void nameValidation(String name) throws FormValidationException {
		if (name == null) {
			throw new FormValidationException(
					"AddComputerForm@nameValidation() : A computer name is required.");
		}
	}

	private void introducedProcess(Computer computer, String introduced) {
		Date date = null;
		try {
			date = dateValidation(introduced, INTRODUCED_ERROR_MESSAGE);
			if (date != null) {
				computer.setIntroduced(new java.sql.Date(date.getTime()));
			} else {
				computer.setIntroduced(null);
			}
			fields.put(FIELD_INTRODUCED, new FormElement(introduced, true));
		} catch (FormValidationException e) {
			fields.put(FIELD_INTRODUCED, new FormElement(introduced, false));
		}
	}

	private void discontinuedProcess(Computer computer, String discontinued) {
		Date date = null;
		try {
			date = dateValidation(discontinued, DISCONTINUED_ERROR_MESSAGE);
			if (date != null) {
				computer.setDiscontinued(new java.sql.Date(date.getTime()));
			} else {
				computer.setDiscontinued(null);
			}
			fields.put(FIELD_DISCONTINUED, new FormElement(discontinued, true));
		} catch (FormValidationException e) {
			fields.put(FIELD_DISCONTINUED, new FormElement(discontinued, false));
		}
	}

	private Date dateValidation(String dateStr, String errorMessage)
			throws FormValidationException {
		if (dateStr == null) {
			return null;
		}
		Date date = null;
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			throw new FormValidationException(errorMessage);
		}
		return date;
	}

	private void companyProcess(Computer computer, String company) {
		Company companyObject = null;
		try {
			companyObject = companyValidation(company);
			computer.setCompany(companyObject);
			fields.put(FIELD_COMPANY, new FormElement(company, true));
		} catch (FormValidationException e) {
			fields.put(FIELD_COMPANY, new FormElement(company, false));
		}
	}

	private Company companyValidation(String company)
			throws FormValidationException {
		if (company == null) {
			return null;
		}

		Company companyObject = null;

		Integer id = null;
		try {
			id = Integer.parseInt(company);
		} catch (NumberFormatException e) {
			throw new FormValidationException(
					"AddComputerForm@companyValidation() : The company id is not an int.");
		}

		companyObject = new Company();
		companyObject.setId(id);

		return companyObject;
	}
}
