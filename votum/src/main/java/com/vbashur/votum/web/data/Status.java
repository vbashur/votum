package com.vbashur.votum.web.data;

public enum Status {
	ERRROR("Error"), SUCCESS("Success"), NONE("None");
	private Status(String val) {
		this.statusValue = val;
	}
	private String statusValue;
	public String getStatusValue() {
		return this.statusValue;
	}
}
