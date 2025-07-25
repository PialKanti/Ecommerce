package com.example.ecommerce_service.repository;

import com.example.ecommerce_service.entity.Customer;
import com.example.ecommerce_service.entity.Order;
import com.example.ecommerce_service.entity.OrderItem;
import com.example.ecommerce_service.entity.Product;
import com.example.ecommerce_service.projection.TopAmountProductProjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Product topSellingProductBySalesAmount;

    @BeforeEach
    void setupTestData() {
        LocalDate today = LocalDate.now();
        Customer testCustomer = createTestCustomer();

        Product shirt = createTestProduct("Shirt", "Shirt description");
        Product pant = createTestProduct("Pant", "Pant description");
        Product sock = createTestProduct("Sock", "Sock description");

        topSellingProductBySalesAmount = pant;

        Order shirtOrder = createTestOrder(today, 1500.0, testCustomer);
        createTestOrderItem(shirtOrder, shirt, 3, 500.0);

        Order pantOrder = createTestOrder(today, 2000.0, testCustomer);
        createTestOrderItem(pantOrder, pant, 2, 1000.0);

        Order sockOrder = createTestOrder(today, 200.0, testCustomer);
        createTestOrderItem(sockOrder, sock, 2, 100.0);
    }

    @Test
    @DisplayName("Returns top selling products by sales amount")
    void testFindTopSellingProductsBySalesAmount(){
        Pageable pageable = PageRequest.of(0, 5);

        Page<TopAmountProductProjection> response = productRepository.findTopSellingProductsBySalesAmount(pageable);

        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSizeLessThanOrEqualTo(5);
        assertProductMatches(response.getContent().getFirst(), topSellingProductBySalesAmount);
    }

    private void assertProductMatches(TopAmountProductProjection actual, Product expected) {
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
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
