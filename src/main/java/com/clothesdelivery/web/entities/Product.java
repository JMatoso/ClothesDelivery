package com.clothesdelivery.web.entities;

import com.clothesdelivery.web.enums.Category;
import com.clothesdelivery.web.enums.ClothesSize;
import com.clothesdelivery.web.enums.ProductFilters;
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
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantityInStock;
    private Category category;
    private String brand;
    private String imageUrl;
    private ClothesSize size;
    private ProductFilters filter;
    private String color;
    private float weight;
    private String friendlyUrl;

    @CreationTimestamp
    private LocalDateTime createdTime;

    public void generateFriendlyUrlFromName() {
        this.friendlyUrl = String.join(name, "-", id.toString()).toLowerCase();
    }

    public void setFriendlyUrl(String name) {
        friendlyUrl = String.join(name, "-", id.toString()).toLowerCase();
    }
}
