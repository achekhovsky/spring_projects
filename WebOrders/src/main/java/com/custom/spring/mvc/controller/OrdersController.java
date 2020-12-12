package com.custom.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Controller
@RequestMapping("/orders")
public class OrdersController {
	private static final Logger LOG = LogManager.getLogger("customLog");
	
	@RequestMapping(method = RequestMethod.GET)
	public String mainPageGet(Model model) {
		return "orders";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String mainPagePost(Model model) {
		return "orders";
	}
	
	@ModelAttribute("forThymeleafTest")
	public String testThymeleaf() {
	    return "modelAttribute";
	}
}
