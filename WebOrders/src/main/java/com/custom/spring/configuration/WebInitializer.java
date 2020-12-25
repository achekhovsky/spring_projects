package com.custom.spring.configuration;

import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.web.Log4jServletContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.custom.spring.configuration.WebAppConfig;
import com.custom.spring.mvc.filters.UrlLangAppenderFilter;
import com.custom.spring.configuration.SimpleUrlHandlerMappingConfig;;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	private static final Logger LOG = LogManager.getLogger("customLog");
	
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        
        //In environments older than Servlet 3.0, this initializer is responsible for starting up Log4j
        //logging before anything else happens in application initialization. 
        //In all environments, this shuts down Log4j after the application shuts down.
        //May cause a memory leak
        //servletContext.addListener(Log4jServletContextListener.class);
        
        servletContext.setInitParameter("isLog4jContextSelectorNamed", "false");
        servletContext.setInitParameter("log4jConfiguration", "classpath:log4j2.xml");
        servletContext.setInitParameter("spring.profiles.active", "production");
        
        servletContext.addFilter("securityFilter", new DelegatingFilterProxy("springSecurityFilterChain"))
          .addMappingForUrlPatterns(null, false, "/*");
        
        LOG.info("Filters count - " + servletContext.getFilterRegistrations().keySet().size());
	     for (String key : servletContext.getFilterRegistrations().keySet()) {
	    	 LOG.info("FILTER - " + key);
	     }
    }
    
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {
				WebAppConfig.class, SecurityConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {SimpleUrlHandlerMappingConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter cef = new CharacterEncodingFilter(
				"UTF-8", 
				true, 
				true);
		return new Filter[] {new HiddenHttpMethodFilter(), new UrlLangAppenderFilter(), cef};
	}
	
//With Boot	
/*	@Bean
	public  FilterRegistrationBean<UrlLangAppenderFilter> logFilter() {
	    FilterRegistrationBean<UrlLangAppenderFilter> registrationBean = new FilterRegistrationBean<>();
	    registrationBean.setFilter(new UrlLangAppenderFilter());
	    registrationBean.addUrlPatterns("/home", "/orders");
	    return registrationBean;
	}*/
}
