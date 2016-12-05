package com.ddf.service;

import com.ddf.domain.*;
import com.ddf.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CartService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final OrderStatusRepository orderStatusRepository;

    private final CartStatusRepository cartStatusRepository;

    @Autowired
    public CartService(
        OrderRepository orderRepository,
        OrderItemRepository orderItemRepository,
        OrderStatusRepository orderStatusRepository,
        CartStatusRepository cartStatusRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.cartStatusRepository = cartStatusRepository;
    }

    public Order submit(Cart cart, Customer customer) {
        Order order = new Order();
        order.setDate(new Date());
        order.setValue(cart.getTotal());
        order.setCustomer(customer);
        order.setCart(cart);
        order.setStatus(this.orderStatusRepository.findOne(OrderStatus.PAYMENT_WAITING));
        this.orderRepository.save(order);

        cart.getItems().forEach(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setTotal(cartItem.getQuantity() * cartItem.getPrice());
            this.orderItemRepository.save(orderItem);
        });

        // Change status of cart to "bought"
        cart.setStatus(this.cartStatusRepository.findOne(CartStatus.BOUGHT));

        return order;
    }
}
