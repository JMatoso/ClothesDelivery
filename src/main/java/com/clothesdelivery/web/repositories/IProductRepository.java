package com.clothesdelivery.web.repositories;

import com.clothesdelivery.web.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> {
    Product findByFriendlyUrl(String friendlyUrl);
}
