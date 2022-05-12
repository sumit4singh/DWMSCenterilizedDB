package com.centdb.model;

public class Column {

	private String columnName;
	
	private Boolean isPrimaryKey = Boolean.FALSE;
	
	private SqlDataType dataType;
	
	private Boolean isForeignKey = Boolean.FALSE;
	
	private String foreignKeyTable;
	
	private String foreignKeyField;
	
	public Column() {
		isPrimaryKey = Boolean.FALSE;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Boolean getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(Boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public SqlDataType getDataType() {
		return dataType;
	}

	public void setDataType(SqlDataType dataType) {
		this.dataType = dataType;
	}

	public Boolean getIsForeignKey() {
		return isForeignKey;
	}

	public void setIsForeignKey(Boolean isForeignKey) {
		this.isForeignKey = isForeignKey;
	}

	public String getForeignKeyTable() {
		return foreignKeyTable;
	}

	public void setForeignKeyTable(String foreignKeyTable) {
		this.foreignKeyTable = foreignKeyTable;
	}

	public String getForeignKeyField() {
		return foreignKeyField;
	}

	public void setForeignKeyField(String foreignKeyField) {
		this.foreignKeyField = foreignKeyField;
	}

	@Override
	public String toString() {
		return "Column [columnName=" + columnName + ", isPrimaryKey=" + isPrimaryKey + ", dataType=" + dataType
				+ ", isForeignKey=" + isForeignKey + ", foreignKeyTable=" + foreignKeyTable + ", foreignKeyField="
				+ foreignKeyField + "]";
	}
}
