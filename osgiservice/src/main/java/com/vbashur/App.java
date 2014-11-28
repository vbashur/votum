package com.vbashur;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpService;

@Component(name = "osgiservice", immediate = true)
@Service(value = App.class)
public class App {

	private static final String SERVLET_ALIAS = "/service";

	@Reference(name = "httpService", referenceInterface = HttpService.class, cardinality = ReferenceCardinality.MANDATORY_UNARY, bind = "bindHttpService", unbind = "unbindHttpService")
	private HttpService httpService;

	protected void bindHttpService(HttpService httpService) {
		System.out.println("Setting the service");
		this.httpService = httpService;
	}

	protected void unbindHttpService(HttpService httpService) {
		System.out.println("UnSetting the service");
		this.httpService = null;
	}

	@Activate
	protected void activate(BundleContext context) throws Exception {
		System.out.println("Servlet activation");
		Dictionary<String, String> props = new Hashtable<String, String>();
		props.put("exec_servlet", ModuleServlet.class.getName());
		ModuleServlet servlet = new ModuleServlet();
		context.registerService(ModuleServlet.class.getName(), servlet, props);
		httpService.registerServlet(SERVLET_ALIAS, servlet, null, null);
		System.out.println("Servlet with alias " + SERVLET_ALIAS + " has been registered");
	}

	@Deactivate
	protected void shutdown() {
		System.out.println("Servlet deactivation");
		httpService.unregister(SERVLET_ALIAS);
	}

}
