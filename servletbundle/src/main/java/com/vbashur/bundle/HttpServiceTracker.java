package com.vbashur.bundle;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

public class HttpServiceTracker extends ServiceTracker {
	
//	private Dictionary props;
//	private BundleContext context;
//	Set aliases = new HashSet();
//	
//	/**
//     * @param		context
//	 * @throws	
//	 */
//	public HttpServiceTracker(BundleContext context) {
//		super(context, HttpService.class.getName(), null);
//		this.context = context;
//	}
//	
//	/**
//     * @param		context
//     * @param		props
//	 * @throws	
//	 */
//	public HttpServiceTracker(BundleContext context, Dictionary props) {
//		super(context, HttpService.class.getName(), null);
//		this.props = props;
//		this.context = context;
//	}
//	
//	/**
//     * @param		reference
//     * @return
//	 * @throws	
//	 */
//	public Object addingService(ServiceReference reference) {
//		HttpService httpService = (HttpService)context.getService(reference);
//		System.out.println(">>>> public Object addingService(ServiceReference reference)");
//		try {
//			System.out.println("Registering servlet, must be true: " + (httpService != null));
//			
//			httpService.registerServlet("/test", new TestServlet("run it!"), props, null);						
//			aliases.add("/test");		
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return httpService;
//	}
//	
//	/**
//	 * @param reference
//	 * @param service
//	 */
//	@Override
//	public void removedService(ServiceReference reference, Object service) {
////		super.removedService(reference, service);
//		try {
//			HttpService httpService = (HttpService)context.getService(reference);
//
//			for (Iterator it = aliases.iterator(); it.hasNext();) {
//			      String alias = (String) it.next();
//			      httpService.unregister(alias);
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}

	private BundleContext context;

	public HttpServiceTracker(BundleContext context) {
		super(context, HttpService.class.getName(), null);
		this.context = context;
		System.out.println("Service Tracking initialization Service initialization");
		System.out.println("Bundles status");
		for (int iter = 0; iter < context.getBundles().length; ++iter) {
			int state = context.getBundles()[iter].getState();
			System.out.println("Bundle: " + context.getBundles()[iter].getSymbolicName() + " : " + getBundleStatus(state));					
		}

	}

	public Object addingService(ServiceReference reference) {
		HttpService httpService = (HttpService) context.getService(reference);
		System.out.println("Adding HTTP Service");
		try {
			httpService.registerServlet(TestServlet.SERVLET_ALIAS,	new TestServlet("Test servlet"), null, null);
		} catch (Exception e) {
			e.printStackTrace();
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

}
