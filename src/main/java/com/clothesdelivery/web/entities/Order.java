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
import java.util.Random;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderReference;

    private Long userId;
    private Long userAddressId;

    private String referenceLocation;

    @Column(length = 1000)
    private String note;

    private int items;

    private String payPalEmail;
    private boolean isPaid;

    private OrderStatus status;

    private BigDecimal total;
    private BigDecimal shipping;

    @CreationTimestamp
    private LocalDateTime createdTime;

    public void generateOrderReference() {
        var CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        var REFERENCE_LENGTH = 10;

        var sb = new StringBuilder();
        var random = new Random();

        for (int i = 0; i < REFERENCE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        this.orderReference = sb.toString();
    }
}
