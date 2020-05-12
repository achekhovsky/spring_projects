package com.custom.spring.db.jpa.listeners;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HiberListenerReg {
	private static final Logger LOG = LogManager.getLogger("customLog");
	@PersistenceUnit
	EntityManagerFactory emf;
	
	@Autowired
	CustomEventListener listener;
	
	@PostConstruct
	public void registerListeners() {
		try {
			SessionFactoryImpl sf = emf.unwrap(SessionFactoryImpl.class);
			EventListenerRegistry registry = sf.getServiceRegistry().getService(EventListenerRegistry.class);
			registry.getEventListenerGroup(EventType.MERGE).appendListener(listener);
			registry.getEventListenerGroup(EventType.DELETE).appendListener(listener);
			registry.getEventListenerGroup(EventType.PERSIST).appendListener(listener);	
			LOG.info("HiberListenerReg::registerListeners: Listeners successfully registered.");
		} catch (Exception e) {
			LOG.info("HiberListenerReg::registerListeners: Listeners not registred.");
		}
	}
}
