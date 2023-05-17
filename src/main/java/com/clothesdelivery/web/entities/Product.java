package com.clothesdelivery.web.entities;

import com.clothesdelivery.web.enums.Category;
import com.clothesdelivery.web.enums.ClothesSize;
import com.clothesdelivery.web.enums.GenreStyle;
import com.clothesdelivery.web.enums.ProductFilters;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
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

    @Column(length = 8000)
    private String description;
    private BigDecimal price;
    private Integer quantityInStock;
    private Category category;
    private GenreStyle genreStyle;
    private boolean isForChildren;
    private String brand;
    private String imageUrl;
    private ClothesSize size;
    private ProductFilters filter;
    private String color;
    private float weight;
    private String friendlyUrl;
    private String sku;

    @CreationTimestamp
    private LocalDateTime createdTime;

    public void generateFriendlyUrlFromName() {
        this.friendlyUrl = String.join(name, "-", id.toString()).toLowerCase();
    }

    public void generateSKU() {
        var initials = name.substring(0, 2).toLowerCase();
        var date = createdTime.toEpochSecond(java.time.ZoneOffset.UTC) * 1000;

        this.sku = initials + date + this.id;
    }

    public void setFriendlyUrl(String name) {
        friendlyUrl = String.join(name, "-", id.toString()).toLowerCase();
    }
}
