package org.platkmframework.boot.jpa.server.runner;

import org.eclipse.jetty.webapp.WebAppContext;
import org.platkmframework.boot.jpa.server.filter.DataBaseFilter;
import org.platkmframework.boot.server.runner.StartServerProcessor;

import jakarta.servlet.Filter;

public class JPAStartServerProcessor extends StartServerProcessor {
	
	public void addSystemCustomFilter(WebAppContext webapp, String[] patterns) {
		
		Filter filter = new DataBaseFilter();
        webapp.getServletHandler().addFilter(newFilterHolder(filter, true), newFilterMapping(filter, patterns));
	}
}
