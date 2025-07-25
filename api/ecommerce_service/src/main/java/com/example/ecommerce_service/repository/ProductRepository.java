package com.example.ecommerce_service.repository;

import com.example.ecommerce_service.entity.Product;
import com.example.ecommerce_service.projection.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
            select orderItem.product.id as id,
                   orderItem.product.name as name,
                   orderItem.product.description as description,
                   coalesce(sum(orderItem.unitPrice * orderItem.quantity), 0.0) as totalSalesAmount
            from OrderItem orderItem
            group by orderItem.product.id
            order by coalesce(sum(orderItem.unitPrice * orderItem.quantity), 0.0) desc
            """)
    Page<ProductProjection> findTopSellingProductsBySalesAmount(Pageable pageable);
}
