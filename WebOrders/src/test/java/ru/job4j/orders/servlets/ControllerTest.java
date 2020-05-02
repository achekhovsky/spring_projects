package ru.job4j.orders.servlets;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class ControllerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		String forParse = null;
		LocalDate ld = LocalDate.parse(forParse == null || forParse == "" ? LocalDate.now().toString() : forParse);
	}

}
