package com.excilys.cdb.form;

import java.util.HashMap;
import java.util.Map;

import com.excilys.cdb.pojo.FormElement;

public abstract class AForm {

	public static final String ATT_FORM = "form";

	protected Map<String, FormElement> fields = new HashMap<String, FormElement>();

	public Map<String, FormElement> getFields() {
		return fields;
	}

	public boolean isValid() {
		boolean valid = true;
		for (Map.Entry<String, FormElement> entry : fields.entrySet()) {
			valid = valid && entry.getValue().isValid();
		}
		return valid;
	}

}
