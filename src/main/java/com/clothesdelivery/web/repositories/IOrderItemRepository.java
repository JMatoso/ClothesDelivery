package com.clothesdelivery.web.repositories;

import com.clothesdelivery.web.entities.Order;
import com.clothesdelivery.web.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrderId(Long orderId);
}
