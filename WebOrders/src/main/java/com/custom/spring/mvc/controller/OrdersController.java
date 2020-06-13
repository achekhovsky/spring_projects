package com.custom.spring.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.custom.spring.db.model.Order;
import com.custom.spring.db.model.OrderImage;
import com.custom.spring.json.Parser;
import com.custom.spring.services.StoreActions;
import com.custom.spring.services.ValidateService;


@Controller
@RequestMapping("/")
public class OrdersController {
	private static final Logger LOG = LogManager.getLogger("customLog");
	@Autowired
	private ValidateService vs;
	
	@Autowired
	ServletContext context;
	
	@RequestMapping(method = RequestMethod.GET)
	public String provideGet(Model model) {
		return "/";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String providePost(Model model) {
		return "/";
	}
	
	@RequestMapping(value = {"/orders"}, method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void processOrders(HttpServletResponse response, HttpServletRequest request, Model uiModel) {
		WebPageParsedParams parsed = fileUpload(createFileUploadHandler(request), request);
		response.setContentType("text/plain");
		String action = parsed.getParams().get("getAction");
		boolean hideRdy = Boolean.parseBoolean(parsed.getParams().get("hideRdy"));
		if (action != null && StoreActions.find(action) != null && !StoreActions.isShowActions(action)) {
			Order ord = new Order(
					parsed.getParams().get("ordername"), 
					parsed.getParams().get("orderdescription"));
			try {
				ord.setId(Integer.parseInt(parsed.getParams().get("id")));
			} catch (NumberFormatException e) {
				LOG.log(Level.ERROR, "OrdersController::provide " + e.getMessage());
				ord.setId(0);
			}
			ord.setDone(Boolean.parseBoolean(parsed.getParams().get("done")));
			ord.setImage(new OrderImage(parsed.getImg()));
			vs.setOrd(ord);
			vs.doAction(action);
		}
		try {
			if (hideRdy) {
				this.sendData(response, Parser.parseToJson(vs.doAction("NOTRDY")));			
			} else {
				this.sendData(response, Parser.parseToJson(vs.doAction("ALL")));	
			}
		} catch (ServletException | IOException e) {
			LOG.log(Level.ERROR, "OrdersController::provide " + e.getMessage());
		}	
	}
	
	private void sendData(HttpServletResponse response, String data) throws ServletException, IOException {
		response.reset();
		response.setContentType("text/plain");
		response.getWriter().append(data);
		response.getWriter().flush();
	}
	
	private ServletFileUpload createFileUploadHandler(HttpServletRequest request) {
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// Configure a repository (to ensure a secure temp location is used)
		File repository = (File) context.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);
		return new ServletFileUpload(factory);
	}
	
	private WebPageParsedParams fileUpload(ServletFileUpload upload, HttpServletRequest request) {
		WebPageParsedParams prms = new WebPageParsedParams();
		// Parse the request
		try {
			List<FileItem> items = upload.parseRequest(request);
			// Process the uploaded items
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
			    FileItem item = iter.next();
			    if (item.isFormField()) {
			    	prms.addParam(item.getFieldName(), item.getString());
			    } else {
			    	prms.setImg(item.get());
			    }
			}
		} catch (FileUploadException e) {
			LOG.log(Level.ERROR, "OrdersController::fileUpload" + e.getMessage());
		}
		return prms;
	}
	
	private static class WebPageParsedParams {
		private Map<String, String> params = new HashMap<>();
		private Optional<byte[]> img;
		
		WebPageParsedParams() {
			img = Optional.empty();
		}

		private Map<String, String> getParams() {
			return params;
		}

		private void setParams(Map<String, String> params) {
			this.params = params;
		}

		private byte[] getImg() {
			return this.img.isPresent() ? this.img.get() : null;
		}

		private void setImg(byte[] img) {
			this.img = Optional.of(img);
		}
		
		private void addParam(String name, String value) {
			this.params.put(name, value);
		}
		
		private void clearParams() {
			this.params.clear();
			this.img = Optional.empty();
		}
	}
}
