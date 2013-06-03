package com.excilys.cdb.pojo;

public class FormElement {

	private String value;
	private boolean valid;

	public FormElement() {
		// Do nothing...
	}

	public FormElement(String value, boolean valid) {
		super();
		this.value = value;
		this.valid = valid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
