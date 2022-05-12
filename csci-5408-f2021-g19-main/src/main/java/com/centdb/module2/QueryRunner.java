package com.centdb.module2;

import com.centdb.model.DatabaseTable;
import com.centdb.model.DeleteQueryModel;
import com.centdb.model.InsertQueryModel;
import com.centdb.model.SelectQueryModel;
import com.centdb.model.UpdateQueryModel;

public class QueryRunner {

	public static void run(String query) {
		try {
			Boolean inValidQuery = Boolean.TRUE;
			if (SyntaxChecker.isUseDatabaseQuery(query)) {
				String databaseName = QueryParser.getDatabaseNameFromUseDatabaseQuery(query);
				if (QueryExecutor.executeUseDatabaseQuery(databaseName)) {
					inValidQuery = Boolean.FALSE;
				}
			} else if (SyntaxChecker.isCreateDatabaseQuery(query)) {
				String databaseName = QueryParser.getDatabaseNameFromCreateDatabaseQuery(query);
				if (QueryExecutor.executeCreateDatabaseQuery(databaseName)) {
					inValidQuery = Boolean.FALSE;
				}
			} else if (SyntaxChecker.isDropDatabaseQuery(query)) {
				String databaseName = QueryParser.getDatabaseNameFromDropDatabaseQuery(query);
				if (QueryExecutor.executeDropDatabaseQuery(databaseName)) {
					inValidQuery = Boolean.FALSE;
				}
			} else if (SyntaxChecker.isCreateTableQuery(query)) {
				DatabaseTable table = QueryParser.getDatabaseTableFromCreateTableQuery(query);
				if (QueryExecutor.executeCreateTableQuery(table, Boolean.FALSE)) {
					inValidQuery = Boolean.FALSE;
				}
			} else if (SyntaxChecker.isDropTableQuery(query)) {
				String tableName = QueryParser.getTabaleNameFromDropTableQuery(query);
				if (QueryExecutor.executeDropTableQuery(tableName, Boolean.FALSE)) {
					inValidQuery = Boolean.FALSE;
				}
			} else if (SyntaxChecker.isTruncateTableQuery(query)) {
				String tableName = QueryParser.getTabaleNameFromTruncateTableQuery(query);
				if (QueryExecutor.executeTruncateTableQuery(tableName, Boolean.FALSE)) {
					inValidQuery = Boolean.FALSE;
				}
			} else if (SyntaxChecker.isSelectQuery(query)) {
				SelectQueryModel selectQuery = QueryParser.getSelectQueryModel(query);
				if (QueryExecutor.executeSelectQuery(selectQuery, Boolean.FALSE)) {
					inValidQuery = Boolean.FALSE;
				}
			} else if (SyntaxChecker.isInsertQuery(query)) {
				InsertQueryModel insertQuery = QueryParser.getInsertQueryModel(query);
				if (QueryExecutor.executeInsertQuery(insertQuery, Boolean.FALSE)) {
					inValidQuery = Boolean.FALSE;
				}
			} else if (SyntaxChecker.isUpdateQuery(query)) {
				UpdateQueryModel updateQuery = QueryParser.getUpdateQueryModel(query);
				if (QueryExecutor.executeUpdateQuery(updateQuery, Boolean.FALSE)) {
					inValidQuery = Boolean.FALSE;
				}
			} else if (SyntaxChecker.isDeleteQuery(query)) {
				DeleteQueryModel deleteQuery = QueryParser.getDeleteQueryModel(query);
				if (QueryExecutor.executeDeleteQuery(deleteQuery, Boolean.FALSE)) {
					inValidQuery = Boolean.FALSE;
				}
			} else {
				System.err.println("Invalid Query.");
			}
			if(inValidQuery) {
				System.err.println("Query cannot be executed: " + query);
			} else {
				System.out.println("Query executed successfullly.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
