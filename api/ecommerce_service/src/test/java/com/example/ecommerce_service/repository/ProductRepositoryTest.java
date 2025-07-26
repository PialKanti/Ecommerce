package com.example.ecommerce_service.repository;

import com.example.ecommerce_service.entity.Customer;
import com.example.ecommerce_service.entity.Order;
import com.example.ecommerce_service.entity.OrderItem;
import com.example.ecommerce_service.entity.Product;
import com.example.ecommerce_service.projection.ProductProjection;
import com.example.ecommerce_service.projection.TopAmountProductProjection;
import com.example.ecommerce_service.projection.TopCountProductProjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Product topSellingProductBySalesAmount;
    private Product topSellingProductBySalesNumber;

    @BeforeEach
    void setupTestData() {
        Customer testCustomer = createTestCustomer();

        Product shirt = createTestProduct("Shirt", "Shirt description");
        Product pant = createTestProduct("Pant", "Pant description");
        Product sock = createTestProduct("Sock", "Sock description");

        topSellingProductBySalesAmount = pant;
        topSellingProductBySalesNumber = shirt;

        YearMonth previousMonth = getPreviousMonth();

        Order shirtOrder = createTestOrder(LocalDate.of(2025, previousMonth.getMonthValue(), 2), 1500.0, testCustomer);
        createTestOrderItem(shirtOrder, shirt, 3, 500.0);

        Order pantOrder = createTestOrder(LocalDate.of(2025, previousMonth.getMonthValue(), 10), 2000.0, testCustomer);
        createTestOrderItem(pantOrder, pant, 2, 1000.0);

        Order sockOrder = createTestOrder(LocalDate.of(2025, previousMonth.getMonthValue(), 20), 200.0, testCustomer);
        createTestOrderItem(sockOrder, sock, 2, 100.0);
    }

    @Test
    @DisplayName("Returns all time top selling products by sales amount")
    void testFindTopSellingProductsBySalesAmount() {
        Pageable pageable = PageRequest.of(0, 5);

        Page<TopAmountProductProjection> response = productRepository.findTopSellingProductsBySalesAmount(pageable);

        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSizeLessThanOrEqualTo(5);
        assertProductMatches(response.getContent().getFirst(), topSellingProductBySalesAmount);
    }

    @Test
    @DisplayName("Returns last month top selling products by sales number")
    void testFindTopSellingProductsBySalesCount() {
        Pageable pageable = PageRequest.of(0, 5);
        YearMonth previousMonth = getPreviousMonth();
        LocalDate startDate = previousMonth.atDay(1);
        LocalDate endDate = previousMonth.atEndOfMonth();

        Page<TopCountProductProjection> pageResponse = productRepository.findTopSellingProductsBySalesCount(startDate, endDate, pageable);

        assertThat(pageResponse).isNotNull();
        assertThat(pageResponse.getContent()).hasSizeLessThanOrEqualTo(5);
        assertProductMatches(pageResponse.getContent().getFirst(), topSellingProductBySalesNumber);
    }

    private void assertProductMatches(ProductProjection actual, Product expected) {
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
    }

    private YearMonth getPreviousMonth() {
        LocalDate today = LocalDate.now();
        return YearMonth.from(today.minusMonths(1));
    }

    private Customer createTestCustomer() {
        Customer customer = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .build();
        return customerRepository.save(customer);
    }

    private Product createTestProduct(String name, String description) {
        Product product = Product.builder()
                .name(name)
                .description(description)
                .isActive(true)
                .build();
        return productRepository.save(product);
    }

    private Order createTestOrder(LocalDate orderDate, Double totalAmount, Customer customer) {
        Order order = Order.builder()
                .customer(customer)
                .orderDate(orderDate)
                .totalAmount(totalAmount)
                .build();
        return orderRepository.save(order);
    }

    public OrderItem createTestOrderItem(Order order, Product product, Integer quantity, Double unitPrice) {
        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .build();
        return orderItemRepository.save(orderItem);
    }
}
