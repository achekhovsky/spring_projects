package com.custom.spring.mvc.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/home")
public class HomeController {
	private static final Logger LOG = LogManager.getLogger("customLog");
	
	@RequestMapping(method = RequestMethod.GET)
	public String mainPageGet(Model model) {
		return "home";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String mainPagePost(Model model) {
		return "home";
	}
}
