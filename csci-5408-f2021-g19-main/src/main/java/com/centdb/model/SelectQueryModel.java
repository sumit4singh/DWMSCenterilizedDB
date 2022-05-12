package com.centdb.model;

import java.util.List;

public class SelectQueryModel {

	private String tableName;
	
	private List<String> columnNameList;
	
	private Boolean selectAllColumn;
	
	private Boolean selectAllRows;
	
	private String conditionColName;
	
	private String conditionColVal;

	public SelectQueryModel(String tableName, List<String> columnNameList, Boolean selectAllColumn,
			Boolean selectAllRows, String conditionColName, String conditionColVal) {
		super();
		this.tableName = tableName;
		this.columnNameList = columnNameList;
		this.selectAllColumn = selectAllColumn;
		this.selectAllRows = selectAllRows;
		this.conditionColName = conditionColName;
		this.conditionColVal = conditionColVal;
	}

	@Override
	public String toString() {
		return "SelectQueryModel [tableName=" + tableName + ", columnNameList=" + columnNameList + ", selectAllColumn="
				+ selectAllColumn + ", selectAllRows=" + selectAllRows + ", conditionColName=" + conditionColName
				+ ", conditionColVal=" + conditionColVal + "]";
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<String> getColumnNameList() {
		return columnNameList;
	}

	public void setColumnNameList(List<String> columnNameList) {
		this.columnNameList = columnNameList;
	}

	public Boolean getSelectAllColumn() {
		return selectAllColumn;
	}

	public void setSelectAllColumn(Boolean selectAllColumn) {
		this.selectAllColumn = selectAllColumn;
	}

	public Boolean getSelectAllRows() {
		return selectAllRows;
	}

	public void setSelectAllRows(Boolean selectAllRows) {
		this.selectAllRows = selectAllRows;
	}

	public String getConditionColName() {
		return conditionColName;
	}

	public void setConditionColName(String conditionColName) {
		this.conditionColName = conditionColName;
	}

	public String getConditionColVal() {
		return conditionColVal;
	}

	public void setConditionColVal(String conditionColVal) {
		this.conditionColVal = conditionColVal;
	}
}
