package com.custom.spring.configuration;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.custom.spring.configuration.WebAppConfig;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.setInitParameter("spring.profiles.active", "production");
    }
    
	@Bean
	public RequestMappingHandlerAdapter getRequestAdapter() {
		return new RequestMappingHandlerAdapter();
	}
	
	@Bean
	public SimpleControllerHandlerAdapter getSimpleControllerAdapter() {
		return new SimpleControllerHandlerAdapter();
	}
	

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {WebAppConfig.class};
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
		CharacterEncodingFilter cef = new CharacterEncodingFilter();
		cef.setEncoding("UTF-8");
		cef.setForceEncoding(true);
		return new Filter[] {new HiddenHttpMethodFilter(), cef};
	}
}