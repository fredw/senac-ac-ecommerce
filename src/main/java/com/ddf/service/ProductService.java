package com.ddf.service;

import com.ddf.Application;
import com.ddf.domain.*;
import com.ddf.repository.*;
import com.ddf.service.exception.ProductCodeDuplicatedException;
import com.ddf.service.exception.ProductFileNotNullException;
import com.ddf.service.exception.ProductImageNotNullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public Product save(Product product) throws ProductCodeDuplicatedException, ProductImageNotNullException, ProductFileNotNullException {

        if (product.getId() == null && product.getImage() == null) {
            throw new ProductImageNotNullException("Image must be not null");
        }

        if (product.getId() == null && product.getFile() == null) {
            throw new ProductFileNotNullException("File must be not null");
        }

        if (product.getId() == null && this.productRepository.findOneByCode(product.getCode()) != null) {
            throw new ProductCodeDuplicatedException("Code already exists");
        }

        if (product.getId() != null) {
            // Product from database
            Product productBefore = this.get(product.getId());

            // When files are not changed, keep them (copy from database)
            if (product.getImage().equals("")) {
                product.setImage(productBefore.getImage());
            }
            if (product.getFile().equals("")) {
                product.setFile(productBefore.getFile());
            }

            // Delete old image when changed
            if (!productBefore.getImage().equals(product.getImage())) {
                try {
                    Files.delete(Paths.get(Application.UPLOAD_PATH + productBefore.getImage()));
                } catch (IOException ignored) {}
            }

            // Delete old file when changed
            if (!productBefore.getFile().equals(product.getFile())) {
                try {
                    Files.delete(Paths.get(Application.UPLOAD_PATH + productBefore.getFile()));
                } catch (IOException ignored) {}
            }
        }

        return this.productRepository.save(product);
    }

    public void delete(Product product) {
        this.productRepository.delete(product);
        try {
            Files.delete(Paths.get(Application.UPLOAD_PATH + product.getImage()));
        } catch (IOException ignored) {}
        try {
            Files.delete(Paths.get(Application.UPLOAD_PATH + product.getFile()));
        } catch (IOException ignored) {}
    }
}
