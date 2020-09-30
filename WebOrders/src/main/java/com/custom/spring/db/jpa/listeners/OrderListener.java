package com.custom.spring.db.jpa.listeners;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.custom.spring.db.model.Order;

public class OrderListener {
	private static final Logger LOG = LogManager.getLogger("customLog");
	
	@PrePersist
	void prePersist(Order ord) {
		LOG.info("OrderListener::prePersist Order in the prePersist process - " + ord);
	}

	@PreUpdate
	void preUpdate(Order ord) {
		LOG.info("OrderListener::preUpdate Order in the preUpdate process - " + ord);
	}

	@PreRemove
	void preRemove(Order ord) {
		LOG.info("OrderListener::preRemove Order in the preRemove process - " + ord);
	}
}