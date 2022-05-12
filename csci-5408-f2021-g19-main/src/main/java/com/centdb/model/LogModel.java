package com.centdb.model;

public class LogModel {

	private String dataBaseName;
	
	private String tableName;

	private String rowCount;

	private String dataBaseState;
	
	private String typeOfQuery;
	
	private String startTime;
	
	private String endTime;
	
	private String queryStatus;

	public String getDataBaseName() {
		return dataBaseName;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getRowCount() {
		return rowCount;
	}

	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}

	public String getDataBaseState() {
		return dataBaseState;
	}

	public void setDataBaseState(String dataBaseState) {
		this.dataBaseState = dataBaseState;
	}

	public String getTypeOfQuery() {
		return typeOfQuery;
	}

	public void setTypeOfQuery(String typeOfQuery) {
		this.typeOfQuery = typeOfQuery;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(String queryStatus) {
		this.queryStatus = queryStatus;
	}
	
}
