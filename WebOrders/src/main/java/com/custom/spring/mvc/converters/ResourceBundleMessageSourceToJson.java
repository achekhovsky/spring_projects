package com.custom.spring.mvc.converters;

import java.util.Locale;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class ResourceBundleMessageSourceToJson extends ReloadableResourceBundleMessageSource {
	private static final Logger LOG = LogManager.getLogger("customLog");
	
/*    public ResourceBundleMessageSourceToJson() {
		//super();
        this.setBasename("classpath:i18n/messages");
        this.setDefaultEncoding("UTF-8");
        this.setCacheSeconds(1);
        this.setUseCodeAsDefaultMessage(true);
	}*/

	public Properties getAllProperties(Locale locale) {
        clearCacheIncludingAncestors();
        PropertiesHolder propertiesHolder = getMergedProperties(locale);
        Properties properties = propertiesHolder.getProperties();
        LOG.debug("ResourceBundle::getAllProperties locale - {} properties - {}", 
        		locale, 
        		properties.toString());
        return properties;
    }
}
