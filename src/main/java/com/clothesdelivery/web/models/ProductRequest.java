package com.clothesdelivery.web.models;

import com.clothesdelivery.web.entities.Product;
import com.clothesdelivery.web.enums.Category;
import com.clothesdelivery.web.enums.ClothesSize;
import com.clothesdelivery.web.enums.GenreStyle;
import com.clothesdelivery.web.enums.ProductFilters;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductRequest {
    private Long id;

    @NotEmpty(message = "Name is a required field.")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 chars.")
    private String name;

    @NotEmpty(message = "Description is a required field.")
    @Size(min = 10, max = 8000, message = "Description must be between 10 and 8000 chars.")
    private String description;

    @DecimalMin(value = "10", message = "Price must be at least 10.")
    private BigDecimal price;

    @Min(value = 0, message = "Quantity must be at least 0.")
    private Integer quantityInStock;

    private Category category;
    private GenreStyle genreStyle;
    private boolean isForChildren;

    @NotEmpty(message = "Brand is a required field.")
    @Size(max = 50, message = "Brand must be below 50 chars.")
    private String brand;

    private ClothesSize size;
    private ProductFilters filter;

    @NotEmpty(message = "Color is a required field.")
    @Size(max = 15, message = "Color must be below 15 chars.")
    private String color;
    private boolean isVisible;

    @DecimalMin(value = "0.0", message = "Weight must be at least 0.0.")
    private float weight;

    public ProductRequest() {}

    public ProductRequest(Long id) {
        this.id = id;
    }

    public Product toProductEntity() {
        var product = new Product();

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
        product.setCreatedTime(LocalDateTime.now());
        product.setVisible(false);

        return product;
    }
}
