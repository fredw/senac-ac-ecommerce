package com.ddf.repository;

import com.ddf.domain.ProductDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductDomain, Long> {

}
