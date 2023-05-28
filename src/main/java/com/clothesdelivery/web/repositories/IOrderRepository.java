package com.clothesdelivery.web.repositories;

import com.clothesdelivery.web.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, Long> {
    Order findByOrderReference(String orderReference);

    List<Order> findAllByUserId(Long userId);
}
