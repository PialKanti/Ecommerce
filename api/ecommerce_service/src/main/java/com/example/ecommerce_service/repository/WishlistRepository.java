package com.example.ecommerce_service.repository;

import com.example.ecommerce_service.entity.Wishlist;
import com.example.ecommerce_service.projection.WishlistItemProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    @Query("""
            select product.id,
                   product.name,
                   product.description
            from Wishlist wishlist
            join Product product
            where wishlist.customer.id = :customerId
            """)
    Page<WishlistItemProjection> findWishlistItemsByCustomerId(Long customerId, Pageable pageable);
}
