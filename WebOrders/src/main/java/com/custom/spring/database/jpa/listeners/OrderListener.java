package com.custom.spring.database.jpa.listeners;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.custom.spring.entities.Order;

public class OrderListener {
	@PrePersist
	void prePersist(Order ord) {
		System.out.println("Order in the prePersist process - " + ord);
	}

	@PreUpdate
	void preUpdate(Order ord) {
		System.out.println("Order in the preUpdate process - " + ord);
	}

	@PreRemove
	void preRemove(Order ord) {
		System.out.println("Order in the preRemove process - " + ord);
	}
}