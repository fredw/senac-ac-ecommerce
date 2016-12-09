package com.ddf.service;

import com.ddf.domain.*;
import com.ddf.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public CustomerService(
        CustomerRepository customerRepository,
        RoleRepository roleRepository,
        OrderRepository orderRepository
    ) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.orderRepository = orderRepository;
    }

    public void save(Customer customer) {
        customer.getUser().setRole(this.roleRepository.findOne(Role.CUSTOMER));
        this.customerRepository.save(customer);
    }

    public Customer get(Long id) {
        return this.customerRepository.findOne(id);
    }

    public Customer getByUser(User user) {
        return this.customerRepository.findOneByUser(user);
    }

    public List<Order> getOrders(Customer customer) {
        return this.orderRepository.findByCustomer(customer);
    }

    public Order getOrder(Customer customer, Long orderId) throws Exception {
        Order order = this.orderRepository.findOne(orderId);
        if (order.getCustomer() != customer) {
            throw new Exception("This order doesn't belong to this customer");
        }
        return order;
    }
}
