package com.tddminiproject.tddminiproject4.repositoryTests;
import com.tddminiproject.tddminiproject4.models.Order;

import com.tddminiproject.tddminiproject4.repositories.OrderRepository;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //this sets up spring app context with a database to test jpa applications
public class OrderRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;



    @Test
    public void testSaveOrder(){
        Order orders = new Order("John Doe", LocalDate.now(), "123 Street, City, State", 200.0);
        Order savedOrder = orderRepository.save(orders);

        Order existingOrder = entityManager.find(Order.class, savedOrder.getId());
        Assertions.assertNotNull(existingOrder);
        Assertions.assertEquals(existingOrder.getCustomerName(), orders.getCustomerName());
        Assertions.assertEquals(existingOrder.getShippingAddress(), orders.getShippingAddress());
        Assertions.assertEquals(existingOrder.getTotal(), orders.getTotal());
    }

    @Test
    public void testPass() {
        // Create an Order object
        Order order = new Order("John Doe", LocalDate.now(), "123 Street, City, State", 200.0);

        // Save the Order object using the repository
        Order savedOrder = orderRepository.save(order);

        // Verify that the Order object is saved and has a valid ID
        Assertions.assertNotNull(savedOrder.getId());
    }

    @Test
    public void testFail() {
        // Create an Order object
        Order order = new Order("John Doe", LocalDate.now(), "123 Street, City, State", 200.0);

        // Save the Order object using the repository
        Order savedOrder = orderRepository.save(order);

        // Verify that the Order object is saved and has a valid ID
        Assertions.assertNotNull(savedOrder.getId()); //to validate that an id number has been assigned.
    }

    @Test
    public void testDeleteNonExistingOrder() {
        // Create a non-existing order ID
        Long nonExistingOrderId = Long.MAX_VALUE; // Assuming this ID does not exist

        // Attempt to fetch the non-existing order
        Optional<Order> order = orderRepository.findById(nonExistingOrderId);

        // Verify that the order is not present
        assertTrue(order.isEmpty());

        // Attempt to delete the non-existing order using deleteById
        orderRepository.deleteById(nonExistingOrderId);

        // Verify that the deletion operation had no effect (no exception thrown)
        // Fetch the order again and assert that it is still not present
        Optional<Order> deletedOrder = orderRepository.findById(nonExistingOrderId);
        assertTrue(deletedOrder.isEmpty());
    }


    @Test
    public void testDeleteOrder() {
        // Create an Order object
        Order order = new Order("John Doe", LocalDate.now(), "123 Street, City, State", 200.0);
        Order savedOrder = entityManager.persistAndFlush(order);

        // Delete the Order using the repository
        orderRepository.deleteById(savedOrder.getId());

        // Verify that the Order is no longer present in the database
        boolean orderExists = orderRepository.existsById(savedOrder.getId());
        Assertions.assertFalse(orderExists);
    }

    @Test
    public void testUpdateOrder() {
        // Create an Order object and save it to the database
        Order order = new Order("John Doe", LocalDate.now(), "123 Street, City, State", 200.0);
        Order savedOrder = entityManager.persistAndFlush(order);

        // Modify the Order object
        savedOrder.setCustomerName("Jane Smith");
        savedOrder.setTotal(300.0);

        // Update the Order using the repository
        Order updatedOrder = orderRepository.save(savedOrder);

        // Verify that the Order is updated with the new values
        Assertions.assertEquals(savedOrder.getCustomerName(), updatedOrder.getCustomerName());
        Assertions.assertEquals(savedOrder.getTotal(), updatedOrder.getTotal());
    }



    @Test
    public void testReadOrder() {
        // Create an Order object and save it to the database
        Order order = new Order("John Doe", LocalDate.now(), "123 Street, City, State", 200.0);
        Order savedOrder = entityManager.persistAndFlush(order);

        // Retrieve the Order from the database using its ID
        Optional<Order> retrievedOrder = orderRepository.findById(savedOrder.getId());

        // Verify that the retrieved Order matches the original Order
        assertTrue(retrievedOrder.isPresent());
        Assertions.assertEquals(savedOrder.getId(), retrievedOrder.get().getId());
        Assertions.assertEquals(savedOrder.getCustomerName(), retrievedOrder.get().getCustomerName());
        Assertions.assertEquals(savedOrder.getShippingAddress(), retrievedOrder.get().getShippingAddress());
        Assertions.assertEquals(savedOrder.getTotal(), retrievedOrder.get().getTotal());
    }

    @Test
    public void testCreateOrder() {
        // Create an Order object
        Order order = new Order("John Doe", LocalDate.now(), "123 Street, City, State", 200.0);

        // Save the Order using the repository
        Order savedOrder = orderRepository.save(order);

        // Verify that the Order is saved with a non-null ID
        Assertions.assertNotNull(savedOrder.getId());
    }
    @Test
    public void testCreateOrder_ValidationErrors() {
        // Create an Order object with invalid data
        Order order = new Order("", null, "", -10.0);

        // Verify that a ConstraintViolationException is thrown when attempting to save the order
        assertThrows(ConstraintViolationException.class, () -> orderRepository.save(order));
    }


}
