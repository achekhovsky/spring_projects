package com.custom.spring.json;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Parser {
	private static final Logger LOG = Logger.getLogger(Parser.class.getName());
	
	public static String parseToJson(Object value) {
		String json = "";
		try {
			ObjectMapper om = new ObjectMapper();
			om.registerModule(new JavaTimeModule());
			om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			json = om.writeValueAsString(value);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Parse::parseToJson", e);
		} 
		return json;
	}
}
