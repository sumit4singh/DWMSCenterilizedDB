package com.centdb.model;

import java.util.ArrayList;
import java.util.List;

public class DatabaseTable {

	private String tableName;
	
	private List<Column> columnList;

	public DatabaseTable() {
		columnList = new ArrayList<>();
	}
	
	@Override
	public String toString() {
		return "DatabaseTable [tabeName=" + tableName + ", columnList=" + columnList + "]";
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<Column> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}
}
