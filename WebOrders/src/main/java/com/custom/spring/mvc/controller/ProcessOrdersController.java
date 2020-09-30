package com.custom.spring.mvc.controller;


import javax.servlet.ServletContext;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.custom.spring.db.model.ObjectsWrapper;
import com.custom.spring.db.model.Order;
import com.custom.spring.json.Parser;
import com.custom.spring.services.StoreActions;
import com.custom.spring.services.ValidateService;


@Controller
@RequestMapping("/")
public class ProcessOrdersController {
	private static final Logger LOG = LogManager.getLogger("customLog");
	@Autowired
	private ValidateService vs;
	@Autowired
	ServletContext context;
	
	@RequestMapping(value = {"/processorders"}, method = RequestMethod.GET)
	@ResponseBody
	public String processOrdersGet(Model model) {
		return "[]";
	}
	
	@RequestMapping(value = {"/processorders"}, method = RequestMethod.POST, 
			consumes = { 
					MediaType.APPLICATION_JSON_VALUE//,
				//	MediaType.MULTIPART_FORM_DATA_VALUE,
				//	MediaType.MULTIPART_MIXED_VALUE},
	},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public String processOrders(
			@RequestBody(required = false) ObjectsWrapper body) {
		if (
			body.getActions().getAction() != null 
			&& StoreActions.find(body.getActions().getAction()) != null 
			&& !StoreActions.isShowActions(body.getActions().getAction())) {
				Order ord = new Order(body.getOrder().getName(), body.getOrder().getDescription());
				try {
					ord.setId(body.getOrder().getId());
				} catch (NumberFormatException e) {
					LOG.log(Level.ERROR, "ProcessOrdersController::processOrders " + e.getMessage());
					ord.setId(0);
				}
				ord.setDone(body.getOrder().isDone());
				try {
					ord.setImage(body.getOrderImage());
				} catch (NullPointerException e) {
					ord.setImage(null);
					LOG.log(Level.ERROR, "ProcessOrdersController::processOrders " + e.getMessage());
				}
				vs.setOrd(ord);
				vs.doAction(body.getActions().getAction());
		}
	
		if (body.getActions().isHideRdy()) {
			return Parser.parseToJson(vs.doAction("NOTRDY"));			
		} else {
			return Parser.parseToJson(vs.doAction("ALL"));	
		}
	}
}
