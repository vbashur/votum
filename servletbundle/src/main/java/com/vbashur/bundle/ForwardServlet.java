package com.vbashur.bundle;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardServlet extends HttpServlet {
	
	/**
	 * 
	 */
	public static final String SERVLET_ALIAS = "/forward";
	
	private static final long serialVersionUID = 1L;
	
	private final String name;

    public ForwardServlet(String name)
    {
        this.name = name;
    }
    
    @Override
    public void init(ServletConfig config)
        throws ServletException
    {
        doLog("Init with config [" + config + "]");
        super.init(config);  
    }

    @Override
    public void destroy()
    {
        doLog("Destroyed servlet");
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();

        out.println("Request = " + req);
        out.println("Forwarded PathInfo = " + req.getPathInfo());
        if (req.getAttribute("A") != null) {
        	out.println("value = " + req.getAttribute("A"));        	
        } else {
        	out.println("no value");
        }
        doLog("Forwarded");        
        getServletContext().log("Name: " + getServletConfig().getServletName());        
        getServletContext().log("Context Name: " + getServletConfig().getServletContext().getServletContextName());
        getServletContext().log("Server Info: " + getServletConfig().getServletContext().getServerInfo());
        getServletContext().log("Context path: " + getServletConfig().getServletContext().getContextPath());
        
        
    }

    private void doLog(String message)
    {
        System.out.println("## [" + this.name + "] " + message);
    }

}
