package com.vbashur;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

public class App {

	private static final String LIB_DIR = "lib";

	private static final String PATH_TO_TARGET_SERVICE = File.separator + "osgiservice" + File.separator + "target" + File.separator + "osgiservice-1.0-SNAPSHOT.jar";

	public static void main(String[] args) {
		
		FrameworkFactory frameworkFactory = ServiceLoader.load(FrameworkFactory.class).iterator().next();
		
		// configuration parameters initialization
		Map<String, String> config = new HashMap<String, String>();
		config.put(Constants.FRAMEWORK_STORAGE_CLEAN, "onFirstInit");
		config.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, "javax.microedition.io");
		System.setProperty("jetty.port", "8080");
		System.setProperty("jetty.home.bundle", "org.eclipse.jetty.osgi.boot");

		Framework framework = frameworkFactory.newFramework(config);
		List<Bundle> installedBundles = new LinkedList<Bundle>();
		try {			
			framework.start();
			System.out.println("Bundles activation started");

			File libsDirectory = new File(LIB_DIR);					
			String libsDirectoryURL = "file:///" + libsDirectory.getAbsolutePath() + File.separator;			
			BundleContext context = framework.getBundleContext();
			
			// installing mandatory bundles
			installedBundles.add(context.installBundle(libsDirectoryURL + "org.eclipse.equinox.util_1.0.500.v20130404-1337.jar"));
			installedBundles.add(context.installBundle(libsDirectoryURL + "org.eclipse.equinox.ds_1.4.200.v20131126-2331.jar"));			
			installedBundles.add(context.installBundle(libsDirectoryURL + "org.eclipse.osgi.services_3.4.0.v20131120-1328.jar"));
			installedBundles.add(context.installBundle(libsDirectoryURL + "org.eclipse.equinox.http.servlet_1.1.400.v20130418-1354.jar"));
			
			// change the _servletVersion variable for installing another version of bundles
			String _servletVersion = "3.0.1"; 			
			String _jetty_version = "8.0.4.v20111024";
			
//			uncomment for 3.1.0 servlet version
//			if (_servletVersion.equals("3.1.0")) {
//				_jetty_version = "9.1.0.v20131115";				
//			}
			
			libsDirectoryURL = libsDirectoryURL + _servletVersion + File.separator;
			
			installedBundles.add(context.installBundle(libsDirectoryURL + "javax.servlet-api-" + _servletVersion + ".jar"));								
			installedBundles.add(context.installBundle(libsDirectoryURL + "jetty-httpservice-" + _jetty_version + ".jar"));

			installedBundles.add(context.installBundle(libsDirectoryURL	+ "jetty-server-" + _jetty_version + ".jar"));
			installedBundles.add(context.installBundle(libsDirectoryURL	+ "jetty-osgi-boot-" + _jetty_version + ".jar"));
			installedBundles.add(context.installBundle(libsDirectoryURL	+ "jetty-deploy-" + _jetty_version + ".jar"));

			installedBundles.add(context.installBundle(libsDirectoryURL	+ "jetty-continuation-" + _jetty_version + ".jar"));
			installedBundles.add(context.installBundle(libsDirectoryURL	+ "jetty-servlet-" + _jetty_version + ".jar"));

			installedBundles.add(context.installBundle(libsDirectoryURL	+ "jetty-webapp-" + _jetty_version + ".jar"));

			installedBundles.add(context.installBundle(libsDirectoryURL	+ "jetty-server-" + _jetty_version + ".jar"));
			installedBundles.add(context.installBundle(libsDirectoryURL	+ "jetty-security-" + _jetty_version + ".jar"));

			installedBundles.add(context.installBundle(libsDirectoryURL	+ "jetty-http-"	+ _jetty_version + ".jar"));
			installedBundles.add(context.installBundle(libsDirectoryURL	+ "jetty-io-" + _jetty_version + ".jar"));
			installedBundles.add(context.installBundle(libsDirectoryURL	+ "jetty-util-"	+ _jetty_version + ".jar"));
			installedBundles.add(context.installBundle(libsDirectoryURL	+ "jetty-xml-" + _jetty_version + ".jar"));

			
			File currentDir = new File(System.getProperty("user.dir"));
			File workspaceDir = currentDir.getParentFile();
			String serviceBundleURL = "file:///" + workspaceDir.getAbsolutePath() + PATH_TO_TARGET_SERVICE; 
			
			
			
			installedBundles.add(context.installBundle(serviceBundleURL));

			for (Bundle bundle : installedBundles) {
				bundle.start();
			}
			System.out.println("Bundles activation is completed");
		} catch (BundleException e) {
			System.out.println("Something goes wrong: " + e.getMessage());
			e.printStackTrace();
		}

	}

}
