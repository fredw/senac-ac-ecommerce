package com.ddf.repository;

import com.ddf.domain.Order;
import com.ddf.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    public List<OrderItem> findAllByOrder(Order order);
}
