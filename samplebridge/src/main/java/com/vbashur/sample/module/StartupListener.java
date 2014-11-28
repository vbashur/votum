package com.vbashur.sample.module;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class StartupListener implements ServletContextListener {

	private static Logger logger = Logger.getLogger(StartupListener.class);

	private FrameworkService service;

	public StartupListener() {
		// TODO Auto-generated constructor stub
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		this.service.stop();
		logger.debug("Servlet bridge context was destroyed");
	}

	public void contextInitialized(ServletContextEvent arg0) {
		logger.debug("Servlet bridge context initialization is started");
		this.service = new FrameworkService(arg0.getServletContext());
		this.service.start();
		logger.debug("Servlet bridge context initialization is completed");
	}

}
