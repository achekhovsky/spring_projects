package com.custom.spring.db.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.custom.spring.db.model.Order;

@Service("jpaOrderService")
@Repository
@Transactional
public class OrderServiceImpl implements OrderService<Order> {
	private static final Logger LOG = LogManager.getLogger("customLog");
	@PersistenceContext
	private EntityManager em;
	
	private OrderServiceImpl() {
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public boolean add(Order ord) {
		//For the correct persistence entity with using the identity generator 
		ord.setId(0);
		try {
			if (ord.getImage() != null) {
				em.persist(ord.getImage());
			}
			em.persist(ord);
		} catch (Exception ex) {
			LOG.log(Level.ERROR, "OrderServiceImpl::add: " + ex.fillInStackTrace().toString());
			ex.printStackTrace();
		} 
		return false;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public boolean deleteOrder(int id) {
		try {
			Order forDel = em.getReference(Order.class, id);
			em.remove(forDel);
		} catch (Exception ex) {
			LOG.log(Level.ERROR, "OrderServiceImpl::deleteOrder: " + ex.getMessage());
			ex.printStackTrace();
		} 
		return false;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public boolean updateStatus(int id, boolean status) {
		try {
			Order forUpd = em.getReference(Order.class, id);
			forUpd.setDone(status);
			em.persist(forUpd);
		} catch (Exception ex) {
			LOG.log(Level.ERROR, "OrderServiceImpl::updateStatus: " + ex.getMessage());
			ex.printStackTrace();
		} 
		return false;
	}

	@Transactional(readOnly = true)
	@Override
	public Order[] getOrders() {
		List<Order> orders = null;
		try {
			orders = em.createNamedQuery(Order.SELECT_ORDERS, Order.class).getResultList();
		} catch (Exception ex) {
			LOG.log(Level.ERROR, "OrderServiceImpl::getOrders: " + ex.getMessage());
			ex.printStackTrace();
		}
		return orders == null ? null : orders.toArray(new Order[orders.size()]);
	}

	@Transactional(readOnly = true)
	@Override
	public Order[] getNotRdyOrders() {
		List<Order> orders = null;
		try {
			orders = em.createNamedQuery(Order.SELECT_NOT_RDY, Order.class).getResultList();
		} catch (Exception ex) {
			LOG.log(Level.ERROR, "OrderServiceImpl::getNotRdyOrders: " + ex.getMessage());
			ex.printStackTrace();
		} 
		return orders.toArray(new Order[orders.size()]);
	}

	@Override
	public Order findById(int id) {
		return em.find(Order.class, id);
	}

	@Override
	public Order findByName(String name) {
		Order result = null;
		try {
			result = em.createNamedQuery(Order.SELECT_BY_NAME, Order.class).setParameter("oNm", name).getSingleResult();
		} catch (Exception e) {
			result = null;
		}
		return result;
	}
}
