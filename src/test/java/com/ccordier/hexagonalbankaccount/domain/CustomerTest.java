package com.ccordier.hexagonalbankaccount.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;


class CustomerTest {

	@Test
	void testCustomerConstructorWithNotNullParameters() throws Exception {

		Customer customer = assertDoesNotThrow(new ThrowingSupplier<Customer>() {
			@Override
			public Customer get() throws Throwable {
				return new Customer("0001", "Gaston", "Lagaffe");
			}
		});

		assertEquals("0001", customer.getClientNumber());
		assertEquals("Gaston", customer.getFirstname());
		assertEquals("Lagaffe", customer.getLastname());

	}

	@Test
	void testCustomerConstructorWithoutClientNumber() throws Exception {

		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> new Customer(null, "Gaston", "Lagaffe"));

		assertEquals("clientNumber is marked non-null but is null", exception.getMessage());

	}

	@Test
	void testCustomerConstructorWitoutFirstname() throws Exception {

		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> new Customer("0001", null, "Lagaffe"));

		assertEquals("firstname is marked non-null but is null", exception.getMessage());

	}

	@Test
	void testCustomerConstructorWithoutLastname() throws Exception {

		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> new Customer("0001", "Gaston", null));

		assertEquals("lastname is marked non-null but is null", exception.getMessage());

	}

	@Test
	void testHashCodeWithSameCustomer() throws Exception {
		Customer customer1 = new Customer("0001", "Gaston", "Lagaffe");
		Customer customer2 = new Customer("0001", "Gaston", "Lagaffe");

		assertTrue(customer1.hashCode() == customer2.hashCode());
	}

	@Test
	void testEqualsWithSameCustomer() throws Exception {
		Customer customer1 = new Customer("0001", "Gaston", "Lagaffe");
		Customer customer2 = new Customer("0001", "Gaston", "Lagaffe");

		assertTrue(customer1.equals(customer2));
	}

	@Test
	void testHashCodeWithNotSameCustomer() throws Exception {
		Customer customer1 = new Customer("0001", "Gaston", "Lagaffe");
		Customer customer2 = new Customer("0002", "Gaston", "Lagaffe");

		assertFalse(customer1.hashCode() == customer2.hashCode());
	}

	@Test
	void testEqualsWithNotSameCustomer() throws Exception {
		Customer customer1 = new Customer("0001", "Gaston", "Lagaffe");
		Customer customer2 = new Customer("0002", "Gaston", "Lagaffe");

		assertFalse(customer1.equals(customer2));
	}

}
