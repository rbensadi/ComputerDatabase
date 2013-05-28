package com.excilys.cdb.form;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.excilys.cdb.pojo.Company;
import com.excilys.cdb.pojo.Computer;
import com.excilys.cdb.service.CompanyServiceImpl;
import com.excilys.cdb.service.ComputerServiceImpl;
import com.excilys.cdb.service.ICompanyService;
import com.excilys.cdb.service.IComputerService;

public class CrudComputerForm extends AForm {

	private static final String INTRODUCED_ERROR_MESSAGE = "AddComputerForm@introducedValidation() : The introduced date has a not the 'yyyy-MM-dd' format.";
	private static final String DISCONTINUED_ERROR_MESSAGE = "AddComputerForm@discontinuedValidation() : The discontinued date has a not the 'yyyy-MM-dd' format.";

	private static final String FIELD_ID = "id";
	private static final String FIELD_NAME = "name";
	private static final String FIELD_INTRODUCED = "introduced";
	private static final String FIELD_DISCONTINUED = "discontinued";
	private static final String FIELD_COMPANY = "company";

	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd",
			Locale.FRANCE);

	private IComputerService computerService;
	private ICompanyService companyService;

	public CrudComputerForm() {
		computerService = ComputerServiceImpl.INSTANCE;
		companyService = CompanyServiceImpl.INSTANCE;
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
				errors.put(FIELD_DISCONTINUED, false);
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

	public Computer getComputer(HttpServletRequest request) {
		String idStr = FormUtils.getFieldValue(request, FIELD_ID);

		Integer id;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			return null;
		}

		Computer computer = computerService.findById(id);

		if (computer == null) {
			return null;
		}

		fields.put(FIELD_NAME, computer.getName());

		if (computer.getIntroduced() != null) {
			fields.put(FIELD_INTRODUCED, df.format(computer.getIntroduced()));
		}

		if (computer.getDiscontinued() != null) {
			fields.put(FIELD_DISCONTINUED,
					df.format(computer.getDiscontinued()));
		}

		if (computer.getCompany() != null) {
			fields.put(FIELD_COMPANY,
					String.valueOf(computer.getCompany().getId()));
		}

		return computer;
	}

	private void nameProcess(Computer computer, String name) {
		try {
			nameValidation(name);
		} catch (FormValidationException e) {
			errors.put(FIELD_NAME, false);
			return;
		}
		computer.setName(name);
		fields.put(FIELD_NAME, name);
		errors.put(FIELD_NAME, true);
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
		} catch (FormValidationException e) {
			fields.put(FIELD_INTRODUCED, introduced);
			errors.put(FIELD_INTRODUCED, false);
			return;
		}
		try {
			computer.setIntroduced(new java.sql.Date(date.getTime()));
		} catch (Exception e) {
			computer.setIntroduced(null);
		}
		fields.put(FIELD_INTRODUCED, introduced);
		errors.put(FIELD_INTRODUCED, true);
	}

	private void discontinuedProcess(Computer computer, String discontinued) {
		Date date = null;
		try {
			date = dateValidation(discontinued, DISCONTINUED_ERROR_MESSAGE);
		} catch (FormValidationException e) {
			fields.put(FIELD_DISCONTINUED, discontinued);
			errors.put(FIELD_DISCONTINUED, false);
			return;
		}
		try {
			computer.setDiscontinued(new java.sql.Date(date.getTime()));
		} catch (Exception e) {
			computer.setDiscontinued(null);
		}
		fields.put(FIELD_DISCONTINUED, discontinued);
		errors.put(FIELD_DISCONTINUED, true);
	}

	private Date dateValidation(String dateStr, String errorMessage)
			throws FormValidationException {
		if (dateStr == null) {
			return null;
		}
		Date date = null;
		String format;
		boolean valid = true;
		try {
			date = df.parse(dateStr);
			format = df.format(date);
			if (!format.equals(dateStr)) {
				valid = false;
			}
		} catch (ParseException e) {
			valid = false;
		}
		if (!valid) {
			throw new FormValidationException(errorMessage);
		}
		return date;
	}

	private void companyProcess(Computer computer, String company) {
		Company companyObject = null;
		try {
			companyObject = companyValidation(company);
		} catch (FormValidationException e) {
			errors.put(FIELD_COMPANY, false);
			return;
		}
		computer.setCompany(companyObject);
		fields.put(FIELD_COMPANY, company);
		errors.put(FIELD_COMPANY, true);
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

		companyObject = companyService.findById(id);
		if (companyObject == null) {
			throw new FormValidationException(
					"AddComputerForm@companyValidation() : The company does not exist.");
		}

		return companyObject;
	}
}
