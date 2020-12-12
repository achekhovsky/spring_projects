package com.custom.spring.mvc.controller;

import java.util.Locale;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.custom.spring.mvc.converters.ResourceBundleMessageSourceToJson;

@Controller
@RequestMapping("/bundle/messages")
public class I18nPropertiesController {
	private static final Logger LOG = LogManager.getLogger("customLog");
    @Autowired
    @Qualifier("messageSource")
    ResourceBundleMessageSourceToJson messageBundle;

    /**
     * Get all locale properties
     */
    @RequestMapping(method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Properties sendProperties(@RequestParam(required = false, defaultValue = "en", name = "lang") String lang) {
    	Properties messages = null; 
    	messages = messageBundle.getAllProperties(StringUtils.parseLocaleString(lang));
        return messages;
    }
}
