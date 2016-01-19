package com.vbashur.votum.web.data;

import java.io.Serializable;

public class OperationResult<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7804437235826387366L;

	private String status;
	
	private T body;

	public String getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status.getStatusValue();
	}

	public T getBody() {
		return body;
	}

	public void setBody(T responseBody) {
		this.body = responseBody;
	}	
		

}
