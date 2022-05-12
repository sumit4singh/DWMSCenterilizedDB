package com.centdb.module3;

import java.util.List;

import com.centdb.constants.DatabaseConstants;
import com.centdb.model.DatabaseTable;
import com.centdb.model.DeleteQueryModel;
import com.centdb.model.InsertQueryModel;
import com.centdb.model.SelectQueryModel;
import com.centdb.model.UpdateQueryModel;
import com.centdb.module2.QueryExecutor;
import com.centdb.module2.QueryParser;
import com.centdb.module2.SyntaxChecker;
import com.centdb.util.Utility;

public class Transaction {

	public static Boolean run(List<String> transactionQueries) {
		// create copy of current database
		Utility.createCopyOfAllFilesInDirectory(DatabaseConstants.DATABASE_PATH + QueryExecutor.currentDatabase);
		for (int i = 1; i < transactionQueries.size() - 1; i++) {
			Boolean inValidQuery = Boolean.TRUE;
			String query = transactionQueries.get(i);
			if (SyntaxChecker.isCreateTableQuery(query)) {
				DatabaseTable table = QueryParser.getDatabaseTableFromCreateTableQuery(query);
				if (QueryExecutor.executeCreateTableQuery(table, Boolean.TRUE)) {
					inValidQuery = Boolean.FALSE;
				}
			} else if (SyntaxChecker.isDropTableQuery(query)) {
				String tableName = QueryParser.getTabaleNameFromDropTableQuery(query);
				if (QueryExecutor.executeDropTableQuery(tableName, Boolean.TRUE)) {
					inValidQuery = Boolean.FALSE;
				}
			} else if (SyntaxChecker.isTruncateTableQuery(query)) {
				String tableName = QueryParser.getTabaleNameFromTruncateTableQuery(query);
				if (QueryExecutor.executeTruncateTableQuery(tableName, Boolean.TRUE)) {
					inValidQuery = Boolean.FALSE;
				}
			} else if (SyntaxChecker.isSelectQuery(query)) {
				SelectQueryModel selectQuery = QueryParser.getSelectQueryModel(query);
				if (QueryExecutor.executeSelectQuery(selectQuery, Boolean.TRUE)) {
					inValidQuery = Boolean.FALSE;
				}
			} else if (SyntaxChecker.isInsertQuery(query)) {
				InsertQueryModel insertQuery = QueryParser.getInsertQueryModel(query);
				if (QueryExecutor.executeInsertQuery(insertQuery, Boolean.TRUE)) {
					inValidQuery = Boolean.FALSE;
				}
			} else if (SyntaxChecker.isUpdateQuery(query)) {
				UpdateQueryModel updateQuery = QueryParser.getUpdateQueryModel(query);
				if (QueryExecutor.executeUpdateQuery(updateQuery, Boolean.TRUE)) {
					inValidQuery = Boolean.FALSE;
				}
			} else if (SyntaxChecker.isDeleteQuery(query)) {
				DeleteQueryModel deleteQuery = QueryParser.getDeleteQueryModel(query);
				if (QueryExecutor.executeDeleteQuery(deleteQuery, Boolean.TRUE)) {
					inValidQuery = Boolean.FALSE;
				}
			}
			if (inValidQuery) {
				System.err.println("Error exists in query : " + query);
				// remove all temp tables
				Utility.deleteTempTables(DatabaseConstants.DATABASE_PATH + QueryExecutor.currentDatabase);
				return Boolean.FALSE;
			}
		}

		// remove all temp tables
		Utility.deleteTempTables(DatabaseConstants.DATABASE_PATH + QueryExecutor.currentDatabase);

		runEachQuery(transactionQueries);
		
		return Boolean.TRUE;
	}
	
	public static Boolean runEachQuery(List<String> transactionQueries) {
		for (int i = 1; i < transactionQueries.size() - 1; i++) {
			Boolean inValidQuery = Boolean.TRUE;
			String query = transactionQueries.get(i);
			if (SyntaxChecker.isCreateTableQuery(query)) {
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
			}
			if (inValidQuery) {
				System.err.println("Error exists on query : " + query);
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}
}
