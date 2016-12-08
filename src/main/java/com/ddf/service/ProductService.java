package com.ddf.service;

import com.ddf.domain.*;
import com.ddf.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    public List<Product> findByFeatured(boolean featured) {
        return this.productRepository.findByFeatured(featured);
    }

    public Product get(Long id) {
        return this.productRepository.findOne(id);
    }

    public Product getByCode(String code) {
        return this.productRepository.findOneByCode(code);
    }

    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    public void delete(Product product) {
        this.productRepository.delete(product);
    }
}
