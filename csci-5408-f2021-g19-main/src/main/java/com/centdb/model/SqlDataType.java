package com.centdb.model;

public enum SqlDataType {

	INT("int"),
	CHAR("char"),
	VARCHAR("varchar"),
	DOUBLE("double");
	
	private String dataType;
	
	private SqlDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getDataType() {
		return this.dataType;
	}
}
