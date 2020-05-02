package com.custom.spring.database;

import com.custom.spring.entities.Order;

public interface Store<T> {
	boolean add(T plc);
	boolean deleteOrder(int id);
	boolean updateStatus(int id, boolean status);
	T[] getOrders();
	T[] getNotRdyOrders();
}

