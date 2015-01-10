package com.vbashur.sample.module;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.FelixConstants;
import org.apache.log4j.Logger;
import org.osgi.framework.BundleException;

public final class FrameworkService {

	private static final String OSGI_PROPERTIES_FILE_LOCATION = "/WEB-INF/framework.properties";

	private static Logger logger = Logger.getLogger(FrameworkService.class);

	private final ServletContext context;
	
	private Felix felix;

	public FrameworkService(ServletContext context) {
		this.context = context;
	}

	public void start() {
		try {
			doStart();
		} catch (IOException e) {
			log("Failed to start OSGI framework. Error occured reading properties file: "
					+ OSGI_PROPERTIES_FILE_LOCATION, e);
		} catch (BundleException e) {
			log("Failed to start OSGI framework", e);
		}
	}

	public void stop() {
		try {
			doStop();
		} catch (BundleException e) {
			log("Failed to stop OSGI framework", e);
		}
	}

	private void doStart() throws IOException, BundleException {
		Felix tmp = new Felix(createConfig());
		tmp.start();
		this.felix = tmp;
		log("OSGi framework started", null);
	}

	private void doStop() throws BundleException {
		if (this.felix != null) {
			this.felix.stop();
		}

		log("OSGi framework stopped", null);
	}

	private Map<String, Object> createConfig() throws IOException {
		Properties props = new Properties();
		props.load(this.context
				.getResourceAsStream(OSGI_PROPERTIES_FILE_LOCATION));

		HashMap<String, Object> map = new HashMap<String, Object>();
		for (Object key : props.keySet()) {
			map.put(key.toString(), props.get(key));
		}

		map.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP,
				Arrays.asList(new ProvisionActivator(this.context)));
		return map;
	}

	private void log(String message, Throwable cause) {
		if (cause != null) {
			logger.error(message, cause);
		} else {
			logger.debug(message);
		}
		this.context.log(message, cause);
	}
}