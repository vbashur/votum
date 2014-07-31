package com.vbashur.samplemanager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;


public class Activator  implements BundleActivator {

	private static BundleContext context;
	
	private ServiceTracker httpServiceTracker;	

	static BundleContext getContext() {
		return context;
	}
	
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("##[" + Activator.class.getName() + "] starting. :" + new java.util.Date());
		Activator.context = bundleContext;			
		httpServiceTracker = new HttpServiceTracker(context);
		httpServiceTracker.open();
		System.out.println("##[" + Activator.class.getName() + "] loaded. :" + new java.util.Date());
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}
	
}
