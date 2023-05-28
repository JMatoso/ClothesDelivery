package com.clothesdelivery.web.repositories;

import com.clothesdelivery.web.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderItemRepository extends JpaRepository<OrderItem, Long> {
}
