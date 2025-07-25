package com.example.ecommerce_service.repository;

import com.example.ecommerce_service.entity.Order;
import com.example.ecommerce_service.projection.MaxSalesDayProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
             select coalesce(sum(order.totalAmount), 0.0)
             from Order order
             where order.orderDate = :date
            """)
    Double getTotalSalesAmountByOrderDate(LocalDate date);

    @Query(value = """
            select daily_sales.order_date AS orderDate,
                   daily_sales.daily_total AS totalSalesAmount
            from (
                select o.order_date,
                      coalesce(sum(o.total_amount), 0.0) as daily_total
                from orders o
                WHERE o.order_date >= :startDate and o.order_date <= :endDate
                group by o.order_date
            ) daily_sales
            where daily_sales.daily_total = (
                select max(max_sales.daily_total)
                from (
                    select cast(coalesce(sum(o.total_amount), 0.0) as double) as daily_total
                    from orders o
                    where o.order_date >= :startDate and o.order_date <= :endDate
                    group by o.order_date
                ) max_sales
            )
            """, nativeQuery = true)
    Page<MaxSalesDayProjection> findMaxSalesDayByOrderDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
