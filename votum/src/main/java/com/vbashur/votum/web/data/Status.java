package com.vbashur.votum.web.data;

public enum Status {
	FAILED("Failed"), SUCCESS("Success"), IGNORED("None");
	private Status(String val) {
		this.statusValue = val;
	}
	private String statusValue;
	public String getStatusValue() {
		return this.statusValue;
	}
}
