package com.ddf.repository;

import com.ddf.domain.Customer;
import com.ddf.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findOneByUser(User user);
}
