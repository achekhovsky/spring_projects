package com.custom.spring.services;

import java.util.HashMap;
import java.util.Map;

import com.custom.spring.database.HiberStore;
import com.custom.spring.database.StoreActions;
import com.custom.spring.entities.Order;

public class ValidateService {
	private static final com.custom.spring.database.Store<Order> STORE = HiberStore.getInstance();
	
	private final Map<StoreActions, java.util.function.Supplier<Object>> actions = new HashMap<>();
	private Order ord = null;
	
	public ValidateService() {
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
		return STORE.add(this.getOrd());
	}
	
	private boolean deleteOrder() {
		return STORE.deleteOrder(this.getOrd().getId());
		
	}
	
	private boolean updateStatus() {
		return STORE.updateStatus(this.getOrd().getId(), this.ord.isDone());
	}
	
	private Order[] getOrders() {
		return STORE.getOrders();
	}
	
	private Order[] getNotRdyOrders() {
		return STORE.getNotRdyOrders();
	}
	
	public Order getOrd() {
		return ord;
	}

	public void setOrd(Order ord) {
		this.ord = ord;
	}
}
