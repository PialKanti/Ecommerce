package com.example.ecommerce_service.repository;

import com.example.ecommerce_service.entity.Customer;
import com.example.ecommerce_service.entity.Product;
import com.example.ecommerce_service.entity.Wishlist;
import com.example.ecommerce_service.projection.WishlistItemProjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class WishlistRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    private Customer testCustomer;

    @BeforeEach
    void setupTestData() {
        testCustomer = createTestCustomer("John", "Doe");
        Product shirt = createTestProduct("Shirt", "Shirt Description");
        createTestProduct("Pant", "Pant Description");

        addProductToWishlist(testCustomer, shirt);
    }

    @Test
    @DisplayName("Returns wishlist items for valid customer")
    void testFindWishlistItemsByCustomerId() {
        Pageable pageable = PageRequest.of(0, 5);

        Page<WishlistItemProjection> pageResponse = wishlistRepository.findWishlistItemsByCustomerId(testCustomer.getId(), pageable);

        assertThat(pageResponse).isNotNull();
        assertThat(pageResponse.getContent().size()).isEqualTo(1);

        WishlistItemProjection item = pageResponse.getContent().getFirst();
        assertThat(item.getName()).isEqualTo("Shirt");
        assertThat(item.getDescription()).isEqualTo("Shirt Description");
    }

    @Test
    @DisplayName("Returns empty list for customer with no wishlist items")
    void testFindWishlistItemsByCustomerId_returnsEmptyForNoItems() {
        Customer anotherCustomer = createTestCustomer("Alice", "Smith");
        Pageable pageable = PageRequest.of(0, 5);

        Page<WishlistItemProjection> result = wishlistRepository
                .findWishlistItemsByCustomerId(anotherCustomer.getId(), pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent().size()).isEqualTo(0);
    }

    private Customer createTestCustomer(String firstName, String lastName) {
        Customer customer = Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .isActive(true)
                .build();
        return customerRepository.save(customer);
    }

    private Product createTestProduct(String name, String description) {
        Product product = Product.builder()
                .name(name)
                .description(description)
                .build();
        return productRepository.save(product);
    }

    private void addProductToWishlist(Customer customer, Product product) {
        Wishlist wishlist = Wishlist.builder()
                .customer(customer)
                .product(product)
                .build();
        wishlistRepository.save(wishlist);
    }
}
