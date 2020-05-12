package com.custom.spring.db.services;

public interface OrderService<T> {
	T findById(int id);
	T findByName(String name);
	boolean add(T plc);
	boolean deleteOrder(int id);
	boolean updateStatus(int id, boolean status);
	T[] getOrders();
	T[] getNotRdyOrders();
}
