package com.centdb.model;

import java.util.List;

public class InsertQueryModel {

	private String tableName;

	private List<List<String>> rowValues;

	public InsertQueryModel(String tableName, List<List<String>> rowValues) {
		super();
		this.tableName = tableName;
		this.rowValues = rowValues;
	}

	@Override
	public String toString() {
		return "InsertQueryModel [tableName=" + tableName + ", rowValues=" + rowValues + "]";
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<List<String>> getRowValues() {
		return rowValues;
	}

	public void setRowValues(List<List<String>> rowValues) {
		this.rowValues = rowValues;
	}
}
