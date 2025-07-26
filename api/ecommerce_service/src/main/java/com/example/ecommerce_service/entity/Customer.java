package com.example.ecommerce_service.entity;

import com.example.ecommerce_service.entity.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends BaseEntity {
    @Column(name = "firstname", length = 25, nullable = false)
    private String firstName;
    @Column(name = "lastname", length = 25)
    private String lastName;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
}
