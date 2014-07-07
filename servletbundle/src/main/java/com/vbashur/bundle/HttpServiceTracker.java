package com.vbashur.bundle;

import javax.servlet.ServletException;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.osgi.util.tracker.ServiceTracker;

public class HttpServiceTracker extends ServiceTracker {
	
	private BundleContext context;

	public HttpServiceTracker(BundleContext context) {
		super(context, HttpService.class.getName(), null);
		this.context = context;
		System.out.println("Service Tracking initialization Service initialization");		
	}

	public Object addingService(ServiceReference reference) {
		HttpService httpService = (HttpService) context.getService(reference);
		System.out.println("Adding HTTP Service");
		try {
			httpService.registerServlet(TestServlet.SERVLET_ALIAS,	new TestServlet("Test servlet"), null, null);
		} catch (ServletException | NamespaceException e) {
			System.err.println("Servlet couldn't be registered: " + e.getMessage());
		} 
		return httpService;
	}

	public void removedService(ServiceReference reference, Object service) {
		super.removedService(reference, service);
		System.out.println("Removed HTTP Service");
	}
	
	public String getBundleStatus(int bundleState) {
		switch (bundleState) {
		case Bundle.ACTIVE:
			return "ACTIVE";
		case Bundle.INSTALLED:
			return "INSTALLED";
		case Bundle.RESOLVED:
			return "RESOLVED";
		case Bundle.UNINSTALLED:
			return "UNINSTALLED";
		default:
			return "N/A";
		}

	}
	
	public void getStatuses() {
		System.out.println("Bundles status");
		for (int iter = 0; iter < context.getBundles().length; ++iter) {
			int state = context.getBundles()[iter].getState();
			System.out.println("Bundle: " + context.getBundles()[iter].getSymbolicName() + " : " + getBundleStatus(state));					
		}
	}

}
