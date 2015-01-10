package com.vbashur.samplemanager;

import javax.servlet.ServletException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.osgi.util.tracker.ServiceTracker;

import com.vbashur.samplemanager.module.CommonServlet;

public class HttpServiceTracker extends ServiceTracker {
	
	private BundleContext context;		
	
	public HttpServiceTracker(BundleContext context) {
		super(context, HttpService.class.getName(), null);
		this.context = context;
	}
	
	public Object addingService(ServiceReference reference) {
		HttpService httpService = (HttpService)context.getService(reference);			
	    try {
			httpService.registerServlet("/common", new CommonServlet(), null, null);
		} catch (ServletException e) {
			// TODO some error handling
			e.printStackTrace();
		} catch (NamespaceException e) {
			// TODO some error handling
			e.printStackTrace();
		}			
		return httpService;
	}
	
	@Override
	public void removedService(ServiceReference reference, Object service) {
		super.removedService(reference, service);		
		HttpService httpService = (HttpService)reference;
		httpService.unregister("/common");
	}
}
