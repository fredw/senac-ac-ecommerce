package com.ddf.repository;

import com.ddf.domain.Customer;
import com.ddf.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAll();

    List<Order> findAllByOrderByIdDesc();

    List<Order> findByCustomer(Customer customer);
}
