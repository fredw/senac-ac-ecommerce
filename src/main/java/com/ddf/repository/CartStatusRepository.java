package com.ddf.repository;

import com.ddf.domain.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartStatusRepository extends JpaRepository<CartStatus, Long> {

}
