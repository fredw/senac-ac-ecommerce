package com.ddf.service;

import com.ddf.domain.Order;
import com.ddf.domain.OrderItem;
import com.ddf.repository.OrderItemRepository;
import com.ddf.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> findByOrder(Order order) {
        return this.orderItemRepository.findAllByOrder(order);
    }

    public OrderItem get(Long id) {
        return this.orderItemRepository.findOne(id);
    }

    public OrderItem save(OrderItem orderItem) {
        return this.orderItemRepository.save(orderItem);
    }
}
