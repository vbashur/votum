package com.vbashur.sampleservletb.module;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vbashur.samplemanager.module.AbstractModuleServlet;

public class ModuleServletForwardToCommon extends AbstractModuleServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getName() {
		return "Module which forwards to Commmon";
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");		
		PrintWriter out = response.getWriter();		
		out.println(doGreetings());
		RequestDispatcher rd = request.getRequestDispatcher("/common");
		rd.forward(request, response);			
	}
}
