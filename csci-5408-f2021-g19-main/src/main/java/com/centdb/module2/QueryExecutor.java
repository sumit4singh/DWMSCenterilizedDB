package com.centdb.module2;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.centdb.LogManagement.SqlLogger;
import com.centdb.constants.DatabaseConstants;
import com.centdb.model.Column;
import com.centdb.model.DatabaseTable;
import com.centdb.model.DeleteQueryModel;
import com.centdb.model.InsertQueryModel;
import com.centdb.model.LogModel;
import com.centdb.model.SelectQueryModel;
import com.centdb.model.UpdateQueryModel;
import com.centdb.util.TableFormat;
import com.centdb.util.Utility;

public class QueryExecutor {

	public static String currentDatabase;

	static {
		currentDatabase = "____";
	}

	public static Boolean executeCreateDatabaseQuery(String databaseName) {
		Date startTime = new Date();
		if(Utility.createDirectory(DatabaseConstants.DATABASE_PATH + databaseName)) {
			Date endTime = new Date();
			log(currentDatabase, "CREATE", startTime, endTime, null, "SUCCESS");
			return Boolean.TRUE;
		}
		Date endTime = new Date();
		log(currentDatabase, "CREATE", startTime, endTime, null, "FAILED");
		return Boolean.FALSE;
	}

	public static Boolean executeUseDatabaseQuery(String databaseName) {
		if (Utility.isFileExists(DatabaseConstants.DATABASE_PATH + databaseName)) {
			currentDatabase = databaseName;
			return Boolean.TRUE;
		} else {
			System.err.println("Database does not exist.");
		}
		return Boolean.FALSE;
	}

	public static Boolean executeDropDatabaseQuery(String databaseName) {
		return Utility.deleteDirectory(DatabaseConstants.DATABASE_PATH + databaseName);
	}

	public static Boolean executeCreateTableQuery(DatabaseTable table, Boolean isTransactionQuery) {
		Date startTime = new Date();
		try {
			if (isTransactionQuery) {
				table.setTableName(table.getTableName() + DatabaseConstants.TMP_FILE);
			}
			String tablePath = DatabaseConstants.DATABASE_PATH + currentDatabase + File.separator + table.getTableName()
					+ DatabaseConstants.TABLE_SUFFIX;
			String metadataPath = DatabaseConstants.DATABASE_PATH + currentDatabase + File.separator
					+ table.getTableName() + DatabaseConstants.METADATA_SUFFIX;
			Utility.createFile(tablePath);
			Utility.writeLineToFile(tablePath, getTableHeader(table));
			Utility.createFile(metadataPath);
			Utility.writeLineToFile(metadataPath, getMetaDataHeader());
			for (Column col : table.getColumnList()) {
				String row = col.getColumnName() + DatabaseConstants.DELIMITER_SYMBOL + col.getDataType().getDataType()
						+ DatabaseConstants.DELIMITER_SYMBOL
						+ (col.getIsPrimaryKey() ? "primary key" : (col.getIsForeignKey()) ? "foreign key" : "")
						+ DatabaseConstants.DELIMITER_SYMBOL
						+ (col.getForeignKeyTable() != null ? col.getForeignKeyTable() : "")
						+ DatabaseConstants.DELIMITER_SYMBOL
						+ (col.getForeignKeyField() != null ? col.getForeignKeyField() : "");
				Utility.writeLineToFile(metadataPath, row);
			}
			Date endTime = new Date();
			log(currentDatabase, "CREATE", startTime, endTime, table.getTableName(), "SUCCESS");
			return Boolean.TRUE;
		} catch (Exception e) {
			e.printStackTrace();
		}

		Date endTime = new Date();
		log(currentDatabase, "CREATE", startTime, endTime, table.getTableName(), "FAILED");
		return Boolean.FALSE;
	}

	public static Boolean executeDropTableQuery(String tableName, Boolean isTransactionQuery) {
		Date startTime = new Date();
		try {
			if (isTransactionQuery) {
				tableName = (tableName + DatabaseConstants.TMP_FILE);
			}
			String tablePath = DatabaseConstants.DATABASE_PATH + currentDatabase + File.separator + tableName
					+ DatabaseConstants.TABLE_SUFFIX;
			String metadataPath = DatabaseConstants.DATABASE_PATH + currentDatabase + File.separator + tableName
					+ DatabaseConstants.METADATA_SUFFIX;
			if(Utility.deleteFile(tablePath) && Utility.deleteFile(metadataPath)) {
				Date endTime = new Date();
				log(currentDatabase, "DROP", startTime, endTime, tableName, "SUCCESS");
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Date endTime = new Date();
		log(currentDatabase, "DROP", startTime, endTime, tableName, "FAILED");
		return Boolean.FALSE;
	}

	public static Boolean executeTruncateTableQuery(String tableName, Boolean isTransactionQuery) {
		Date startTime = new Date();
		try {
			if (isTransactionQuery) {
				tableName = (tableName + DatabaseConstants.TMP_FILE);
			}
			String tablePath = DatabaseConstants.DATABASE_PATH + currentDatabase + File.separator + tableName
					+ DatabaseConstants.TABLE_SUFFIX;
			String firstLine = Utility.readFirstLine(tablePath);
			Utility.deleteContentOfFile(tablePath);
			if(Utility.writeLineToFile(tablePath, firstLine)) {
				Date endTime = new Date();
				log(currentDatabase, "TRUNCATE", startTime, endTime, tableName, "SUCCESS");
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date endTime = new Date();
		log(currentDatabase, "TRUNCATE", startTime, endTime, tableName, "FAILED");
		return Boolean.FALSE;
	}

	public static Boolean executeSelectQuery(SelectQueryModel selectQuery, Boolean isTransactionQuery) {
		Date startTime = new Date();
		try {
			if (isTransactionQuery) {
				selectQuery.setTableName(selectQuery.getTableName() + DatabaseConstants.TMP_FILE);
			}
			Boolean isAllColumn = selectQuery.getSelectAllColumn();
			String tablePath = DatabaseConstants.DATABASE_PATH + currentDatabase + File.separator
					+ selectQuery.getTableName() + DatabaseConstants.TABLE_SUFFIX;
			List<String> selectedColumn = selectQuery.getColumnNameList().stream().map(col -> col.toLowerCase())
					.collect(Collectors.toList());
			List<String[]> table = getTable(tablePath);
			Map<Integer, Boolean> isValidColumn = new TreeMap<>();
			Integer whereColumnId = -1;
			List<String[]> tableView = new ArrayList<>();

			List<String> header = new ArrayList<>();
			for (int i = 0; i < table.get(0).length; i++) {
				if (isAllColumn || selectedColumn.contains(table.get(0)[i].toLowerCase())) {
					isValidColumn.put(i, Boolean.TRUE);
					header.add(table.get(0)[i]);
				} else {
					isValidColumn.put(i, Boolean.FALSE);
				}
				if (Boolean.FALSE.equals(selectQuery.getSelectAllRows())
						&& selectQuery.getConditionColName().equals(table.get(0)[i])) {
					whereColumnId = i;
				}
			}
			tableView.add(header.toArray(new String[0]));

			for (int i = 1; i < table.size(); i++) {
				List<String> rowData = new ArrayList<>();
				Boolean conditionSatisfy = Boolean.TRUE;
				for (int j = 0; j < table.get(i).length; j++) {
					if (isValidColumn.get(j)) {
						rowData.add(table.get(i)[j]);
					}
					if (j == whereColumnId) {
						conditionSatisfy = (table.get(i)[j].equals(selectQuery.getConditionColVal()));
					}
				}
				if (conditionSatisfy)
					tableView.add(rowData.toArray(new String[0]));
			}
			if (isTransactionQuery) {
				Date endTime = new Date();
				log(currentDatabase, "SELECT", startTime, endTime, selectQuery.getTableName(), "SUCCESS");
				return Boolean.TRUE;
			}
			TableFormat.printTable(tableView);

			Date endTime = new Date();
			log(currentDatabase, "SELECT", startTime, endTime, selectQuery.getTableName(), "SUCCESS");
			return Boolean.TRUE;
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date endTime = new Date();
		log(currentDatabase, "SELECT", startTime, endTime, selectQuery.getTableName(), "FAILED");
		return Boolean.FALSE;
	}

	public static Boolean executeUpdateQuery(UpdateQueryModel updateQuery, Boolean isTransactionQuery) {
		Date startTime = new Date();
		try {
			if (isTransactionQuery) {
				updateQuery.setTableName(updateQuery.getTableName() + DatabaseConstants.TMP_FILE);
			}
			String tablePath = DatabaseConstants.DATABASE_PATH + currentDatabase + File.separator
					+ updateQuery.getTableName() + DatabaseConstants.TABLE_SUFFIX;
			List<String[]> table = getTable(tablePath);
			Integer updateColIdx = -1;
			Integer conColIdx = -1;
			for (int i = 0; i < table.get(0).length; i++) {
				if (updateQuery.getUpdateColName().equalsIgnoreCase(table.get(0)[i])) {
					updateColIdx = i;
				}
				if (Boolean.FALSE.equals(updateQuery.getUpdateAll())
						&& updateQuery.getConditionColName().equalsIgnoreCase(table.get(0)[i])) {
					conColIdx = i;
				}
			}
			if (updateQuery.getUpdateAll()) {
				for (int i = 1; i < table.size(); i++)
					table.get(i)[updateColIdx] = updateQuery.getUpdateColValue();
				Utility.deleteContentOfFile(tablePath);
				insertMultipleRows(tablePath, table);

				Date endTime = new Date();
				log(currentDatabase, "UPDATE", startTime, endTime, updateQuery.getTableName(), "SUCCESS");
				return Boolean.TRUE;
			}
			final Integer conditionColIdx = conColIdx;
			final Integer updateColumnIdx = updateColIdx;
			table = table.stream().map(row -> {
				String[] updatedRow = row;
				if (row[conditionColIdx].equalsIgnoreCase(updateQuery.getConditionColVal())) {
					updatedRow[updateColumnIdx] = updateQuery.getUpdateColValue();
				}
				return updatedRow;
			}).collect(Collectors.toList());

			Utility.deleteContentOfFile(tablePath);
			insertMultipleRows(tablePath, table);

			Date endTime = new Date();
			log(currentDatabase, "UPDATE", startTime, endTime, updateQuery.getTableName(), "SUCCESS");
			return Boolean.TRUE;
		} catch (Exception e) {
			e.printStackTrace();
		}

		Date endTime = new Date();
		log(currentDatabase, "UPDATE", startTime, endTime, updateQuery.getTableName(), "FAILED");
		return Boolean.FALSE;
	}

	public static Boolean executeInsertQuery(InsertQueryModel insertQuery, Boolean isTransactionQuery) {
		Date startTime = new Date();
		try {
			if (isTransactionQuery) {
				insertQuery.setTableName(insertQuery.getTableName() + DatabaseConstants.TMP_FILE);
			}
			String tablePath = DatabaseConstants.DATABASE_PATH + currentDatabase + File.separator
					+ insertQuery.getTableName() + DatabaseConstants.TABLE_SUFFIX;
			Integer primaryKeyColumnIndex = -1;
			String metadataPath = DatabaseConstants.DATABASE_PATH + currentDatabase + File.separator
					+ insertQuery.getTableName() + DatabaseConstants.METADATA_SUFFIX;
			List<String[]> metadata = getTable(metadataPath);
			for (int i = 1; i < metadata.size(); i++) {
				if (metadata.get(i).length > 2 && metadata.get(i)[2] != null && metadata.get(i)[2].length() >= 11
						&& metadata.get(i)[2].equalsIgnoreCase("primary key")) {
					primaryKeyColumnIndex = i - 1;
				}
			}
			Set<String> primaryKeyValues = new TreeSet<>();
			if (primaryKeyColumnIndex != -1) {
				List<String[]> table = getTable(tablePath);
				for (String[] row : table) {
					primaryKeyValues.add(row[primaryKeyColumnIndex]);
				}
			}
			List<String> writeInDatabase = new ArrayList<>();
			for (List<String> rowVal : insertQuery.getRowValues()) {
				StringBuilder builder = new StringBuilder();
				builder.append(rowVal.get(0));
				for (int i = 1; i < rowVal.size(); i++) {
					builder.append(DatabaseConstants.DELIMITER_SYMBOL);
					builder.append(rowVal.get(i));
				}
				if (primaryKeyColumnIndex != -1 && primaryKeyValues.contains(rowVal.get(primaryKeyColumnIndex))) {
					System.err.println("Duplicate value in primary key column.");

					Date endTime = new Date();
					int tableRowC = getTable(tablePath).size();
					log(currentDatabase, "INSERT", startTime, endTime, insertQuery.getTableName(), "FAILED", Integer.toString(tableRowC));
					return Boolean.FALSE;
				}
				if (primaryKeyColumnIndex != -1)
					primaryKeyValues.add(rowVal.get(primaryKeyColumnIndex));
				String row = builder.toString();
				writeInDatabase.add(row);
			}
			for (String row : writeInDatabase) {
				Utility.writeLineToFile(tablePath, row);
			}

			Date endTime = new Date();
			int tableRowC = getTable(tablePath).size();
			log(currentDatabase, "INSERT", startTime, endTime, insertQuery.getTableName(), "SUCCESS",Integer.toString(tableRowC) );
			return Boolean.TRUE;
		} catch (Exception e) {
			e.printStackTrace();
		}

		Date endTime = new Date();

		log(currentDatabase, "INSERT", startTime, endTime, insertQuery.getTableName(), "FAILED");
		return Boolean.FALSE;
	}

	public static Boolean executeDeleteQuery(DeleteQueryModel deleteQuery, Boolean isTransactionQuery) {
		Date startTime = new Date();
		try {
			if (isTransactionQuery) {
				deleteQuery.setTableName(deleteQuery.getTableName() + DatabaseConstants.TMP_FILE);
			}
			if (deleteQuery.getDeleteAll()) {
				executeTruncateTableQuery(deleteQuery.getTableName(), isTransactionQuery);
				Date endTime = new Date();
				log(currentDatabase, "DELETE", startTime, endTime, deleteQuery.getTableName(), "SUCCESS");
				return Boolean.TRUE;
			}
			String tablePath = DatabaseConstants.DATABASE_PATH + currentDatabase + File.separator
					+ deleteQuery.getTableName() + DatabaseConstants.TABLE_SUFFIX;
			List<String[]> table = getTable(tablePath);
			Integer columnIndex = -1;
			for (int i = 0; i < table.get(0).length; i++) {
				if (deleteQuery.getConditionColName().equals(table.get(0)[i])) {
					columnIndex = i;
				}
			}
			final Integer conditionColumnIndex = columnIndex;
			table = table.stream()
					.filter(row -> !row[conditionColumnIndex].equalsIgnoreCase(deleteQuery.getConditionColVal()))
					.collect(Collectors.toList());
			Utility.deleteContentOfFile(tablePath);
			insertMultipleRows(tablePath, table);

			Date endTime = new Date();
			log(currentDatabase, "DELETE", startTime, endTime, deleteQuery.getTableName(), "SUCCESS", Integer.toString(table.get(0).length));
			return Boolean.TRUE;
		} catch (Exception e) {
			e.printStackTrace();
		}

		Date endTime = new Date();
		log(currentDatabase, "DELETE", startTime, endTime, deleteQuery.getTableName(), "FAILED");
		return Boolean.FALSE;
	}

	private static void insertMultipleRows(String filePath, List<String[]> rows) {
		try {
			for (String[] row : rows) {
				Utility.writeLineToFile(filePath, convertToRow(row));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getMetaDataHeader() {
		return new StringBuilder().append("column").append(DatabaseConstants.DELIMITER_SYMBOL).append("type")
				.append(DatabaseConstants.DELIMITER_SYMBOL).append("constraints")
				.append(DatabaseConstants.DELIMITER_SYMBOL).append("foreign_key_table")
				.append(DatabaseConstants.DELIMITER_SYMBOL).append("foreign_key_col").toString();
	}

	private static String getTableHeader(DatabaseTable table) {
		return String.join(DatabaseConstants.DELIMITER_SYMBOL,
				table.getColumnList().stream().map(col -> col.getColumnName()).collect(Collectors.toList()));
	}

	private static String convertToRow(String[] arr) {
		return String.join("|", arr);
	}

	public static List<String[]> getTable(String tablePath) {
		try {
			List<String> rows = Utility.readFileByLines(new File(tablePath));
			String[] headers = rows.get(0).split("\\|");
			List<String[]> tableView = new ArrayList<>();
			List<String> header = new ArrayList<>();
			for (int i = 0; i < headers.length; i++) {
				header.add(headers[i]);
			}
			tableView.add(header.toArray(new String[0]));
			for (int i = 1; i < rows.size(); i++) {
				String[] row = rows.get(i).split("\\|");
				List<String> rowData = new ArrayList<>();
				for (int j = 0; j < row.length; j++) {
					rowData.add(row[j]);
				}
				tableView.add(rowData.toArray(new String[0]));
			}
			return tableView;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void log(String databaseName, String typeOfQuery, Date startTime, Date endTime, String tableName, String queryStatus, String... args) {
		LogModel logModel = new LogModel();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logModel.setDataBaseName(databaseName);
		logModel.setTableName(tableName);
		logModel.setStartTime(formatter.format(startTime));
		logModel.setEndTime(formatter.format(endTime));
		logModel.setTypeOfQuery(typeOfQuery);
		if(args.length > 0){

			logModel.setRowCount(args[0]);
		}
		logModel.setQueryStatus(queryStatus);
		logModel.setDataBaseState("query");
		new SqlLogger(logModel).queryLog();
		new SqlLogger(logModel).eventLog();
		new SqlLogger(logModel).generalLog();
	}
}
