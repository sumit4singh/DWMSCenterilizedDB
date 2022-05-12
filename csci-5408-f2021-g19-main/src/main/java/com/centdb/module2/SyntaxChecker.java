package com.centdb.module2;

import com.centdb.constants.DatabaseConstants.RegExConstants;

public class SyntaxChecker {

	public static Boolean isUseDatabaseQuery(String query) {
		return query.matches(RegExConstants.USE_DATABASE_QUERY_REGEX);
	}

	public static Boolean isCreateDatabaseQuery(String query) {
		return query.matches(RegExConstants.CREATE_DATABASE_QUERY_REGEX);
	}

	public static Boolean isDropDatabaseQuery(String query) {
		return query.matches(RegExConstants.DROP_DATABASE_QUERY_REGEX);
	}

	public static Boolean isCreateTableQuery(String query) {
		return query.matches(RegExConstants.CREATE_TABLE_QUERY_REGEX);
	}

	public static Boolean isDropTableQuery(String query) {
		return query.matches(RegExConstants.DROP_TABLE_QUERY_REGEX);
	}

	public static Boolean isTruncateTableQuery(String query) {
		return query.matches(RegExConstants.TRUNCATE_TABLE_QUERY_REGEX);
	}

	public static Boolean isSelectQuery(String query) {
		return query.matches(RegExConstants.SELECT_QUERY_REGEX);
	}

	public static Boolean isInsertQuery(String query) {
		return query.matches(RegExConstants.INSERT_QUERY_REGEX);
	}

	public static Boolean isUpdateQuery(String query) {
		return query.matches(RegExConstants.UPDATE_QUERY_REGEX);
	}

	public static Boolean isDeleteQuery(String query) {
		return query.matches(RegExConstants.DELETE_QUERY_REGEX);
	}
}
