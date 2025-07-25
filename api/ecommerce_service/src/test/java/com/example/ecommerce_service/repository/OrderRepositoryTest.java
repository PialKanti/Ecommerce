package com.example.ecommerce_service.repository;

import com.example.ecommerce_service.entity.Customer;
import com.example.ecommerce_service.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Double expectedDailySalesTotal;

    @BeforeEach
    void setupTestData() {
        Customer customer = createTestCustomer("John", "Doe");

        Double[] testSalesAmount = {100.0, 200.0, 300.50, 400.80, 500.00};

        expectedDailySalesTotal = Arrays.stream(testSalesAmount)
                .mapToDouble(salesAmount -> {
                    createTestOrder(LocalDate.now(), salesAmount, customer);
                    return salesAmount;
                })
                .sum();
    }

    @Test
    @DisplayName("Returns total sales for today")
    void testGetTotalSalesAmountByOrderDate() {
        LocalDate today = LocalDate.now();

        Double actualSalesAmount = orderRepository.getTotalSalesAmountByOrderDate(today);

        assertThat(actualSalesAmount).isNotNull();
        assertThat(actualSalesAmount).isEqualTo(expectedDailySalesTotal);
    }

    private Order createTestOrder(LocalDate orderDate, Double totalAmount, Customer customer) {
        Order order = Order.builder()
                .customer(customer)
                .orderDate(orderDate)
                .totalAmount(totalAmount)
                .build();
        return orderRepository.save(order);
    }

    private Customer createTestCustomer(String firstName, String lastName) {
        Customer customer = Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .isActive(true)
                .build();
        return customerRepository.save(customer);
    }
}
