package ru.job4j.database;

import static org.junit.Assert.*;


import org.apache.logging.log4j.LogManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import com.custom.spring.configuration.WebAppConfig;
import com.custom.spring.db.model.Order;
import com.custom.spring.db.model.OrderImage;
import com.custom.spring.db.services.OrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class})
@ActiveProfiles(value = "test")
public class DBInteractionTest {
	private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(DBInteractionTest.class.getName());
	@Autowired
	private OrderService<Order> ordServ;
	@Autowired
	private WebApplicationContext wac;
	
	@Test
	public void shouldFindAllOrders() throws Exception {
		Order[] orders = ordServ.getOrders();
		assertEquals(2, orders.length);
	}
	
	@Test
	public void shouldFindAllNotRdyOrders() throws Exception {
		Order[] orders = ordServ.getNotRdyOrders();
		assertEquals(1, orders.length);
	}
	
	@Test
	public void shouldFindFirstOrderById() throws Exception {
		Order order = ordServ.findById(1);
		assertEquals("order_1", order.getName());
	}
	
	@Test
	public void shouldFindFirstOrderByName() throws Exception {
		Order order = ordServ.findByName("order_1");
		assertEquals(1, order.getId());
	}
	
	@Test
	public void shouldUpdateStatus() throws Exception {
		assertEquals(false, ordServ.findById(1).isDone());
		ordServ.updateStatus(1, true);
		assertEquals(true, ordServ.findById(1).isDone());
	}
	
	@Test
	public void shouldCreateOrderAndImg() throws Exception {
		String name = "NewOrder";
		String description = "NewDescription";
		byte[] imgData = new byte[] {'1', '0', '3', '0', '4', '0',
				'5', '0', '6', '0', '7', '0', '8', '0', '9', '0', 'a', '0',
				'b', '0', 'c', '0', 'd', '0', 'e', '0', 'f'};
		Order ord = new Order(name, description);
		OrderImage oimg = new OrderImage(imgData);
		ord.setImage(oimg);	
	    ordServ.add(ord);			
	assertNotNull("ID не может быть пустым", ord.getId());  
	assertNotNull("ID не может быть пустым", oimg.getId());
	Order result = ordServ.findByName(name);
	assertEquals(description, result.getDescription());
	assertArrayEquals(imgData, result.getImage().getImg());
	}
}
