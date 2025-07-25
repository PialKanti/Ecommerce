package com.example.ecommerce_service.repository;

import com.example.ecommerce_service.entity.Product;
import com.example.ecommerce_service.projection.TopAmountProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
            select product.id as id,
                   product.name as name,
                   product.description as description,
                   sales.totalSalesAmount as totalSalesAmount
            from Product product
            inner join (
                 select orderItem.product.id as productId,
                        coalesce(sum(orderItem.unitPrice * orderItem.quantity), 0.0) as totalSalesAmount
                 from OrderItem orderItem
                 group by orderItem.product.id
            ) sales
                     on product.id = sales.productId
            where product.isActive = true
            order by sales.totalSalesAmount desc
            """)
    Page<TopAmountProductProjection> findTopSellingProductsBySalesAmount(Pageable pageable);
}
