package com.clothesdelivery.web.entities;

import com.clothesdelivery.web.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ordersHistory")
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Long userId;
    private BigDecimal totalPrice;
    private int quantity;
    private OrderStatus status;
    private boolean isPaid;

    @CreationTimestamp
    private LocalDateTime addedDate;
}
