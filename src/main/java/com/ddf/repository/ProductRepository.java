package com.ddf.repository;

import com.ddf.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public Product findOneByCode(String code);

    public List<Product> findByFeatured(boolean featured);
}
