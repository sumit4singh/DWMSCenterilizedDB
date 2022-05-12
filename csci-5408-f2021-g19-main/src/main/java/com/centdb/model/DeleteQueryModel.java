package com.centdb.model;

public class DeleteQueryModel {

	private String tableName;
	
	private Boolean deleteAll;
	
	private String conditionColName;
	
	private String conditionColVal;

	public DeleteQueryModel(String tableName, Boolean deleteAll, String conditionColName, String conditionColVal) {
		super();
		this.tableName = tableName;
		this.deleteAll = deleteAll;
		this.conditionColName = conditionColName;
		this.conditionColVal = conditionColVal;
	}

	@Override
	public String toString() {
		return "DeleteQueryModel [tableName=" + tableName + ", deleteAll=" + deleteAll + ", conditionColName="
				+ conditionColName + ", conditionColVal=" + conditionColVal + "]";
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Boolean getDeleteAll() {
		return deleteAll;
	}

	public void setDeleteAll(Boolean deleteAll) {
		this.deleteAll = deleteAll;
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
