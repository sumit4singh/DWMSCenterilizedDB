package com.centdb.model;

public class UpdateQueryModel {

	private String tableName;
	
	private Boolean updateAll;
	
	private String updateColName;
	
	private String updateColValue;
	
	private String conditionColName;
	
	private String conditionColVal;

	public UpdateQueryModel(String tableName, Boolean updateAll, String updateColName, String updateColValue,
			String conditionColName, String conditionColVal) {
		super();
		this.tableName = tableName;
		this.updateAll = updateAll;
		this.updateColName = updateColName;
		this.updateColValue = updateColValue;
		this.conditionColName = conditionColName;
		this.conditionColVal = conditionColVal;
	}

	@Override
	public String toString() {
		return "UpdateQueryModel [tableName=" + tableName + ", updateAll=" + updateAll + ", updateColName="
				+ updateColName + ", updateColValue=" + updateColValue + ", conditionColName=" + conditionColName
				+ ", conditionColVal=" + conditionColVal + "]";
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Boolean getUpdateAll() {
		return updateAll;
	}

	public void setUpdateAll(Boolean updateAll) {
		this.updateAll = updateAll;
	}

	public String getUpdateColName() {
		return updateColName;
	}

	public void setUpdateColName(String updateColName) {
		this.updateColName = updateColName;
	}

	public String getUpdateColValue() {
		return updateColValue;
	}

	public void setUpdateColValue(String updateColValue) {
		this.updateColValue = updateColValue;
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
