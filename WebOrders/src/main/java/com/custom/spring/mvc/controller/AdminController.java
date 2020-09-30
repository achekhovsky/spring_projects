package com.custom.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public String adminPageGet(Model model) {
		return "admin";
	}
}
