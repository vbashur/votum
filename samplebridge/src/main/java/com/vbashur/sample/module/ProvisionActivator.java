package com.vbashur.sample.module;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

public class ProvisionActivator implements BundleActivator {

	private final static String OSGI_BUNDLE_PATH = "/WEB-INF/bundles/";
	
	private final ServletContext servletContext;
	
	private static final String DEFAULT_BUNDLE_EXTENSION = ".jar";

	public ProvisionActivator(ServletContext servletContext) {
		this.servletContext = servletContext;
		
	}

	public void start(BundleContext context) throws Exception {
		servletContext.setAttribute(BundleContext.class.getName(), context);		
		
		for (String str : this.servletContext.getServletRegistrations().keySet()) {
			this.servletContext.log("Registered: " + str);
		}
		
		
				
		ArrayList<Bundle> installed = new ArrayList<Bundle>();
		for (URL url : findBundles()) {
			this.servletContext.log("Installing bundle [" + url + "]");
			Bundle bundle = context.installBundle(url.toExternalForm());
			installed.add(bundle);
		}

		for (Bundle bundle : installed) {
			if (!isFragment(bundle)) {
				bundle.start();
			}
		}
	}

	public void stop(BundleContext arg0) throws Exception {
	}

	private boolean isFragment(Bundle bundle) {
		return bundle.getHeaders().get(Constants.FRAGMENT_HOST) != null;
	}

	private List<URL> findBundles() throws Exception {
		ArrayList<URL> list = new ArrayList<URL>();
		for (Object o : this.servletContext.getResourcePaths(OSGI_BUNDLE_PATH)) {
			String name = (String) o;
			if (name.endsWith(DEFAULT_BUNDLE_EXTENSION)) {
				URL url = this.servletContext.getResource(name);
				if (url != null) {
					list.add(url);
				}
			}
		}

		return list;
	}

}
