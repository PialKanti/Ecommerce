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

    private Customer testCustomer;
    private LocalDate today;

    @BeforeEach
    void setupTestData() {
        today = LocalDate.now();
        testCustomer = createTestCustomer("John", "Doe");
    }

    @Test
    @DisplayName("Returns total sales for today")
    void testGetTotalSalesAmountByOrderDate() {
        Double[] testSalesAmount = {100.0, 200.0, 300.50, 400.80, 500.00};

        Double expectedDailySalesTotal = Arrays.stream(testSalesAmount)
                .mapToDouble(salesAmount -> {
                    createTestOrder(today, salesAmount, testCustomer);
                    return salesAmount;
                })
                .sum();

        Double actualSalesAmount = orderRepository.getTotalSalesAmountByOrderDate(today);

        assertThat(actualSalesAmount).isNotNull();
        assertThat(actualSalesAmount).isEqualTo(expectedDailySalesTotal);
    }

    @Test
    @DisplayName("Returns zero sales for today when table is empty")
    void testGetTotalSalesAmountByOrderDate_tableEmpty() {
        Double expectedDailySalesTotal = 0.0;

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
