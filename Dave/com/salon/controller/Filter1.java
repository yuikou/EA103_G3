package com.salon.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Filter1 implements Filter {
	private FilterConfig config;
  

    public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}
    
	public void destroy() {
		config = null;
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();		
		
		Object salonVO = session.getAttribute("salonVO");
		if (salonVO == null) {
			session.setAttribute("location", req.getRequestURI());
//			res.sendRedirect(req.getContextPath() + "/front-end/salon/salonregi.jsp");
			res.sendRedirect(req.getContextPath() + "/front-end/salon/Glogin.jsp");
			return;
		} else {
			chain.doFilter(request, response);
		}
		
	}

	
	

}
