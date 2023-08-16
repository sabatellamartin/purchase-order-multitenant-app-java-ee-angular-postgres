package com.app.distrity.util.tenant;

import com.app.distrity.util.Constants;

public class DbUtil {

	private static final String MAIN_SCHEMA = Constants.MASTER_TENANT;

	public static String buildSchemaString(String schemaName) {
		return schemaName;
	}

	public static String buildSchemaString() {
		return MAIN_SCHEMA;
	}

}
