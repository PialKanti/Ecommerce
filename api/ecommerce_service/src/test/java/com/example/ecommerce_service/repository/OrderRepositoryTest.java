package com.example.ecommerce_service.repository;

import com.example.ecommerce_service.entity.Customer;
import com.example.ecommerce_service.entity.Order;
import com.example.ecommerce_service.projection.MaxSaleDayProjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer testCustomer;
    private LocalDate today;

    @BeforeEach
    void setupTestData() {
        today = LocalDate.now();
        testCustomer = createTestCustomer();
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


    @Test
    @DisplayName("Returns single result from max sales days")
    void testFindMaxSalesDayByOrderDateRange() {
        LocalDate[] testDates = {
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 2),
                LocalDate.of(2025, 1, 1)
        };
        Double[] salesAmount = {100.0, 200.0, 300.50};
        Pageable pageable = PageRequest.of(0, 5);

        IntStream.range(0, testDates.length).forEach(i -> createTestOrder(testDates[i], salesAmount[i], testCustomer));
        Double expectedMaxSalesAmount = salesAmount[0] + salesAmount[2];

        Page<MaxSaleDayProjection> response = orderRepository.findMaxSalesDayByOrderDateRange(LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 31), pageable);

        assertThat(response).isNotNull();
        assertThat(response.getContent().size()).isEqualTo(1);
        assertThat(response.getContent().getFirst().getTotalSalesAmount()).isEqualTo(expectedMaxSalesAmount);
    }

    @Test
    @DisplayName("Returns multiple result from max sales days")
    void testFindMaxSalesDayByOrderDateRange_multipleResult() {
        LocalDate[] testDates = {
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 2),
                LocalDate.of(2025, 1, 3),
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 2)
        };

        Double[] salesAmounts = {100.0, 200.0, 400.0, 300.0, 100.0};

        IntStream.range(0, testDates.length).forEach(i -> orderRepository.save(createTestOrder(testDates[i], salesAmounts[i], testCustomer)));

        Pageable pageable = PageRequest.of(0, 10);

        Double expectedMaxSalesAmount = 400.0;

        Page<MaxSaleDayProjection> result = orderRepository.findMaxSalesDayByOrderDateRange(
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 3),
                pageable
        );

        assertThat(result).isNotNull();
        assertThat(result.getContent().size()).isGreaterThan(1);
        assertThat(result.getContent())
                .allSatisfy(item -> assertThat(item.getTotalSalesAmount()).isEqualTo(expectedMaxSalesAmount));
    }

    private Order createTestOrder(LocalDate orderDate, Double totalAmount, Customer customer) {
        Order order = Order.builder()
                .customer(customer)
                .orderDate(orderDate)
                .totalAmount(totalAmount)
                .build();
        return orderRepository.save(order);
    }

    private Customer createTestCustomer() {
        Customer customer = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .build();
        return customerRepository.save(customer);
    }
}
