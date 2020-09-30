package ru.job4j.orders.servlets;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.custom.spring.configuration.JpaConfiguration;
import com.custom.spring.configuration.SecurityConfiguration;
import com.custom.spring.configuration.WebAppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, JpaConfiguration.class, SecurityConfiguration.class})
@WithMockUser(username = "user", roles = {"USER"})
@ActiveProfiles(value = "test")
public class ControllerTest {
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
//	WebClient webClient;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void ifGetAllThenTwoOrdersInResult() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/processorders")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content("{\"order\":{},"
						+ "\"actions\":{\"action\":\"ALL\", \"hideRdy\":\"false\"},"
						+ "\"orderImage\":{\"img\":null}}");
		this.mockMvc.perform(builder)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json("["
					+ "{\"id\":1,\"createDate\":\"2019-04-25\",\"name\":\"order_1\",\"description\":\"description_1\",\"done\":false,\"image\":{\"id\":1,\"img\":\"AQMEBQYHCAkKCwwNDg8=\"}},"
					+ "{\"id\":2,\"createDate\":\"2019-04-25\",\"name\":\"order_2\",\"description\":\"description_2\",\"done\":true,\"image\":{\"id\":2,\"img\":\"AQMEBQYHCAkKCwwNDg8=\"}}"
					+ "]"))
			.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void ifDeleteOneOrderThenOneOrderInResult() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/processorders")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content("{\"order\":{\"id\":1, \"name\":\"order_1\", \"description\":\"description_1\"},"
						+ "\"actions\":{\"action\":\"DELETE\", \"hideRdy\":\"false\"},"
						+ "\"orderImage\":{\"img\":null}}");
		this.mockMvc.perform(builder)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json("["
					+ "{\"id\":2,\"createDate\":\"2019-04-25\",\"name\":\"order_2\",\"description\":\"description_2\",\"done\":true,\"image\":{\"id\":2,\"img\":\"AQMEBQYHCAkKCwwNDg8=\"}}"
					+ "]"))
			.andDo(MockMvcResultHandlers.print());
	}
}
