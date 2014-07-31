package com.vbashur.sampleservletb.module;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vbashur.samplemanager.module.AbstractModuleServlet;

public class ModuleServletForwardToSpring extends AbstractModuleServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getName() {
		return "Module which forwards to Spring MVC";
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(doGreetings());
		ServletContext sc = getServletContext();
		sc = sc.getContext("/samplebridge");

		RequestDispatcher rd = sc.getRequestDispatcher("/hi");
//		rd.forward(request, response); // won't work
		rd.include(request, response);

	}

}
