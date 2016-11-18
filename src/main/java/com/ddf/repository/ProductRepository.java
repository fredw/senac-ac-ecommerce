package com.ddf.repository;

import com.ddf.domain.ProductDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductDomain, Long> {

    public List<ProductDomain> findByFeatured(boolean featured);
}
