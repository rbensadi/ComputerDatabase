package com.excilys.cdb.form;

import java.util.HashMap;
import java.util.Map;

public abstract class AForm {

	public static final String ATT_FORM = "form";
	public static final String ATT_FIELDS = "fields";
	public static final String ATT_ERRORS = "errors";

	protected Map<String, String> fields = new HashMap<String, String>();
	protected Map<String, Boolean> errors = new HashMap<String, Boolean>();

	public Map<String, String> getFields() {
		return fields;
	}

	public Map<String, Boolean> getErrors() {
		return errors;
	}

	public boolean isValid() {
		boolean valid = true;
		for (Map.Entry<String, Boolean> entry : errors.entrySet()) {
			valid = valid && entry.getValue();
		}
		return valid;
	}

	protected void setError(String field, Boolean error) {
		errors.put(field, error);
	}

}
