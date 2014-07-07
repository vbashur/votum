package com.vbashur.serv;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupListener implements ServletContextListener {

	private FrameworkService service;

	public StartupListener() {
		// TODO Auto-generated constructor stub
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("contextDestroyed");
		this.service.stop();

	}

	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("contextInitialized");
		this.service = new FrameworkService(arg0.getServletContext());
		this.service.start();

	}

}
