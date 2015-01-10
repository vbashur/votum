package com.vbashur.samplemanager.module;

import javax.servlet.http.HttpServlet;

public abstract class AbstractModuleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String doGreetings() {
		return "My name is " + getName();
	}
	
	public abstract String getName();
}
