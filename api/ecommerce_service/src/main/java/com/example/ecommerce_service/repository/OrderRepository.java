package com.example.ecommerce_service.repository;

import com.example.ecommerce_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
             select sum(order.totalAmount)
             from Order order
             where order.orderDate = :date
            """)
    Double getTotalSalesAmountByOrderDate(LocalDate date);
}
