package com.vbashur.bundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Hello world!
 * 
 */
public class Activator implements BundleActivator {
	// private ServiceRegistration registration;
	//
	// private ServiceTracker httpServiceTracker;
	//
	// public void start(BundleContext context) throws Exception {
	// System.out.println("AAD Bundle activator start");
	// Hashtable props = new Hashtable();
	// props.put("alias", "/test");
	// props.put("init.message", "Hello World!");
	// props.put("contextId", "true");
	// props.put("context.shared", "true");
	// System.out.println("AAD map: " + props.toString());
	// this.registration = context.registerService(TestServlet.class.getName(),
	// new TestServlet("Test Servlet"), props);
	// System.out.println("AAD Bundle activator stop");
	// System.out.println("AAD Must be true: " +
	// (this.registration.getReference() != null));
	// }
	//
	// public void stop(BundleContext context) throws Exception {
	// this.registration.unregister();
	// }
	//
	// public void start(BundleContext bundleContext) throws Exception {
	// System.out.println("##[" + Activator.class.getName() +
	// "] starting .... new BUNDLE SERVLET ACTIVATOR:" + new java.util.Date());
	//
	// Dictionary props = new Hashtable();
	// props.put(org.osgi.framework.Constants.SERVICE_VENDOR,
	// "Example company name");
	// props.put("config_dir", "/" +
	// bundleContext.getBundle().getSymbolicName());
	// props.put("exec_servlet", TestServlet.class.getName());
	//
	// TestServlet module = new TestServlet("TestServlet");
	// bundleContext.registerService(TestServlet.class.getName(), module,
	// props);
	//
	// httpServiceTracker = new HttpServiceTracker(bundleContext, props);
	// httpServiceTracker.open();
	// System.out.println("##[" + Activator.class.getName() +
	// "] BUNDLE SERVLET ACTIVATOR loaded. :" + new java.util.Date());
	// }

	private static BundleContext context;

	private ServiceTracker httpServiceTracker;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("\n##[" + Activator.class.getName()	+ "] starting.... :" + new java.util.Date());
		Activator.context = bundleContext;
		httpServiceTracker = new HttpServiceTracker(context);
		httpServiceTracker.open();
		System.out.println("##[" + Activator.class.getName() + "] loaded. :" + new java.util.Date());
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;

		httpServiceTracker.close();
	}

}
