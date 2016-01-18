package com.vbashur.votum.web.data;

import java.io.Serializable;

public class OperationResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7804437235826387366L;

	private String status;
	
	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status.getStatusValue();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
		

}
