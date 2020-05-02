package ru.job4j.database;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.custom.spring.entities.Order;
import com.custom.spring.entities.OrderImage;

public class DBInteractionTest {
	private static EntityManagerFactory emf =
			Persistence.createEntityManagerFactory("chapter03OrderTest");
	private EntityManager em;
	private EntityTransaction tx;
	
	@Before
	public void initEntityManager() throws Exception {
		em = emf.createEntityManager();
		tx = em.getTransaction();
	}
	
	@After
	public void closeEntityManager() throws Exception {
		if (em != null) {
			em.close();
		}
	}
	
	@Test
	public void shouldFindNewOrder() throws Exception {
		Order order = em.find(Order.class, 1);
		assertEquals("order_1", order.getName());
	}
	
	@Test
	public void shouldCreateOrderAndImg() throws Exception {
		byte[] imgData = new byte[] {'1', '0', '3', '0', '4', '0',
				'5', '0', '6', '0', '7', '0', '8', '0', '9', '0', 'a', '0',
				'b', '0', 'c', '0', 'd', '0', 'e', '0', 'f'};
		Order ord = new Order("NewOrder", "NewDescription");
		OrderImage oimg = new OrderImage(imgData);
		ord.setImage(oimg);	
	tx.begin();
	em.persist(ord);
	em.persist(oimg);
	tx.commit();
	assertNotNull("ID не может быть пустым", ord.getId());  
	assertNotNull("ID не может быть пустым", oimg.getId());
	ord = em.find(Order.class, ord.getId());
	assertEquals("NewDescription", ord.getDescription());
	assertEquals(imgData, ord.getImage().getImg());
	}
}
