package com.custom.spring.database;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.custom.spring.entities.Order;
import com.custom.spring.entities.OrderImage;


public class HiberStore implements Store<Order> {
	private static final Logger LOG = LogManager.getLogger("customLog");
	private static final HiberStore STORE = new HiberStore();
	private SessionFactory sessionFactory;
	
	private HiberStore() {
		initFactory("META-INF/listener.xml");
	}
	
	public static HiberStore getInstance() {
		return STORE;
	}
	
	private void initFactory(String listenerURL) {
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures settings
																									// from
																									// hibernate.cfg.xml
				.build();
		try {
			sessionFactory = new MetadataSources(registry)
			//parses Order, OrderImage classes for mapping annotations		
//		    .addAnnotatedClass(Order.class)
//		    .addAnnotatedClass(OrderImage.class)
		    .addResource(listenerURL)
		    .getMetadataBuilder()
		    .applyAttributeConverter(LDConverter.class, false)
		    .build()
		    .buildSessionFactory();
		} catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble
			// building the SessionFactory
			// so destroy it manually.
			LOG.log(Level.ERROR, "HiberStore::initFactory: " + e.getMessage());
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}

	@Override
	public boolean add(Order ord) {
		Session session = null;
		//For the correct persistence entity with using the identity generator 
		ord.setId(0);
		try {
			session = sessionFactory.openSession();
			session.getTransaction().begin();
			session.persist(ord);
			if (ord.getImage() != null) {
				session.persist(ord.getImage());
			}
			session.getTransaction().commit();
		} catch (Exception ex) {
			LOG.log(Level.ERROR, "HiberStore::add: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return false;
	}

	@Override
	public boolean deleteOrder(int id) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.getTransaction().begin();
			Order forDel = session.getReference(Order.class, id);
			session.delete(forDel);
			session.getTransaction().commit();
		} catch (Exception ex) {
			LOG.log(Level.ERROR, "HiberStore::deleteOrder: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return false;
	}

	@Override
	public boolean updateStatus(int id, boolean status) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.getTransaction().begin();
			Order forDel = session.getReference(Order.class, id);
			forDel.setDone(status);
			session.getTransaction().commit();
		} catch (Exception ex) {
			LOG.log(Level.ERROR, "HiberStore::updateStatus: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return false;
	}

	@Override
	public Order[] getOrders() {
		Session session = null;
		List<Order> orders = null;
		try {
			session = sessionFactory.openSession();
			orders = (List<Order>) session.createNamedQuery(Order.SELECT_ORDERS).list();
		} catch (Exception ex) {
			LOG.log(Level.ERROR, "HiberStore::getOrders: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return orders == null ? null : orders.toArray(new Order[orders.size()]);
	}

	@Override
	public Order[] getNotRdyOrders() {
		Session session = null;
		List<Order> orders = null;
		try {
			session = sessionFactory.openSession();
			orders = (List<Order>) session.createNamedQuery(Order.SELECT_NOT_RDY).list();
		} catch (Exception ex) {
			LOG.log(Level.ERROR, "HiberStore::getNotRdyOrders: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return orders.toArray(new Order[orders.size()]);
	}

}
