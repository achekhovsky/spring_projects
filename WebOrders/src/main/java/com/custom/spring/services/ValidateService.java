package com.custom.spring.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.custom.spring.configuration.JpaConfiguration;
import com.custom.spring.db.model.Order;
import com.custom.spring.db.services.OrderService;

public class ValidateService {
	
	private AnnotationConfigApplicationContext ctx;
	private OrderService<Order> store;
	
	private final Map<StoreActions, java.util.function.Supplier<Object>> actions = new HashMap<>();
	private Order ord = null;
	
	public ValidateService() {
		ctx = new AnnotationConfigApplicationContext();
		ctx.getEnvironment().setActiveProfiles("production");
		ctx.register(JpaConfiguration.class);
		ctx.refresh();
		store = ctx.getBean(OrderService.class);
		
		actions.put(StoreActions.ALL, this::getOrders);
		actions.put(StoreActions.NOTRDY, this::getNotRdyOrders);
		actions.put(StoreActions.ADD, this::add);
		actions.put(StoreActions.DELETE, this::deleteOrder);
		actions.put(StoreActions.UPDATE, this::updateStatus);
	}	
	
	public Object doAction(String action) {
		return this.actions.get(StoreActions.valueOf(action)).get();
	}
	
	private boolean add() {
		return store.add(this.getOrd());
	}
	
	private boolean deleteOrder() {
		return store.deleteOrder(this.getOrd().getId());
		
	}
	
	private boolean updateStatus() {
		return store.updateStatus(this.getOrd().getId(), this.ord.isDone());
	}
	
	private Order[] getOrders() {
		return store.getOrders();
	}
	
	private Order[] getNotRdyOrders() {
		return store.getNotRdyOrders();
	}
	
	public Order getOrd() {
		return ord;
	}

	public void setOrd(Order ord) {
		this.ord = ord;
	}
}
