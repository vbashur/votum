package com.vbashur.bundle;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class TestServlet extends HttpServlet {
	
	/**
	 * 
	 */
	public static final String SERVLET_ALIAS = "/service";
	
	private static final long serialVersionUID = 1L;
	
	private final String name;

    public TestServlet(String name)
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
        out.println("PathInfo = " + req.getPathInfo());
        req.setAttribute("A", "OKAY");
        String url = "";
        Set keys = getServletContext().getServletRegistrations().keySet();
        Iterator iter = keys.iterator();
        while (iter.hasNext()) {
			getServletContext().log("Registered2: " + iter.next());						
		}
        getServletContext().log("Name: " + getServletConfig().getServletName());
        getServletContext().log("Context Name: " + getServletConfig().getServletContext().getServletContextName());
        getServletContext().log("Server Info: " + getServletConfig().getServletContext().getServerInfo());
        getServletContext().log("Context path: " + getServletConfig().getServletContext().getContextPath());
        ServletContext sc = getServletContext().getContext("/servletbridge");
        RequestDispatcher rd = sc.getRequestDispatcher("/forward");        
//        RequestDispatcher rd = req.getRequestDispatcher(ForwardServlet.SERVLET_ALIAS); // Looks like a bug
        rd.forward(req, res);
    }

    private void doLog(String message)
    {
        System.out.println("## [" + this.name + "] " + message);
    }

}
