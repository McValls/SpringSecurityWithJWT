package com.mcvalls.configuration.initializer;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import com.mcvalls.configuration.security.SpringSecurityConfigurer;
import com.mcvalls.rest.ApplicationContext;

/**
 * 
 * @author mcvalls
 * 
 * Deployment Descriptor
 *	
 */
public class WebAppInitializer implements WebApplicationInitializer{

	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		addListenersToContext(servletContext, rootContext);
		addConfigurationClassesToRootContext(rootContext);
		addFiltersToContext(servletContext);
		addDispatcherServlet(servletContext);
	}

	private void addListenersToContext(ServletContext context, AnnotationConfigWebApplicationContext rootContext) {
		context.addListener(new ContextLoaderListener(rootContext));
	}
	
	private void addConfigurationClassesToRootContext(AnnotationConfigWebApplicationContext rootContext) {
		rootContext.register(SpringSecurityConfigurer.class);
		rootContext.register(ApplicationContext.class);
	}
	
	/**
	 * Necessary for REST mapping
	 * @param context
	 */
	private void addDispatcherServlet(ServletContext context) {
		AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();
		dispatcherServlet.register(ApplicationContext.class);
		ServletRegistration.Dynamic dispatcher = context.addServlet("dispatcher",
				new DispatcherServlet(dispatcherServlet));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/*");
	}
	
	/**
	 * All request will be filtered by this filter chain (notice the "/*" url-pattern mapping
	 * 
	 * @param context ServletContext to which the Spring Security Filter Chain will be added.
	 * 
	 */
	private void addFiltersToContext(ServletContext context) {
		FilterRegistration filter = context.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
		filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
	}
	
}
