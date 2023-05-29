package com.clothesdelivery.web.entities;

import com.clothesdelivery.web.enums.Category;
import com.clothesdelivery.web.enums.ClothesSize;
import com.clothesdelivery.web.enums.GenreStyle;
import com.clothesdelivery.web.enums.ProductFilters;
import com.clothesdelivery.web.models.ProductRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.jetbrains.annotations.NotNull;

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
    private boolean isVisible;
    private float weight;
    private String friendlyUrl;
    private String sku;

    @CreationTimestamp
    private LocalDateTime createdTime;

    public void generateFriendlyUrlFromName() {
        var nameJoined = String.join("-", name.split(" ")).toLowerCase();
        this.friendlyUrl = nameJoined + "-" + this.id;
    }

    public void generateSKU() {
        var initials = name.substring(0, 2).toLowerCase();
        var date = createdTime.toEpochSecond(java.time.ZoneOffset.UTC) * 1000;

        this.sku = initials + date + this.id;
    }

    public void setFriendlyUrl(String name) {
        friendlyUrl = String.join(name, "-", id.toString()).toLowerCase();
    }

    public Product update(@NotNull Product product) {
        this.name = product.getName();
        this.category = product.getCategory();
        this.size = product.getSize();
        this.genreStyle = product.getGenreStyle();
        this.filter = product.getFilter();
        this.description = product.getDescription();
        this.quantityInStock = product.getQuantityInStock();
        this.price = product.getPrice();
        this.color = product.getColor();
        this.weight = product.getWeight();
        this.brand = product.getBrand();
        this.isForChildren = product.isForChildren();
        this.createdTime = product.getCreatedTime();
        this.isVisible = product.isVisible();

        this.generateFriendlyUrlFromName();
        this.generateSKU();

        return this;
    }

    public Product update(@NotNull ProductRequest product) {
        this.name = product.getName();
        this.category = product.getCategory();
        this.size = product.getSize();
        this.genreStyle = product.getGenreStyle();
        this.filter = product.getFilter();
        this.description = product.getDescription();
        this.quantityInStock = product.getQuantityInStock();
        this.price = product.getPrice();
        this.color = product.getColor();
        this.weight = product.getWeight();
        this.brand = product.getBrand();
        this.isForChildren = product.isForChildren();
        this.createdTime = LocalDateTime.now();
        this.isVisible = product.isVisible();

        this.generateFriendlyUrlFromName();
        this.generateSKU();

        return this;
    }

    public ProductRequest toProductRequest() {
        var product = new ProductRequest();

        product.setId(id);
        product.setName(name);
        product.setCategory(category);
        product.setSize(size);
        product.setGenreStyle(genreStyle);
        product.setFilter(filter);
        product.setDescription(description);
        product.setQuantityInStock(quantityInStock);
        product.setPrice(price);
        product.setColor(color);
        product.setWeight(weight);
        product.setBrand(brand);
        product.setForChildren(isForChildren);
        product.setVisible(false);

        return product;
    }
}
