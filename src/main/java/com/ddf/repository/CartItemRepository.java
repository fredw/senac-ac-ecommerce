package com.ddf.repository;

import com.ddf.domain.CartItem;
import com.ddf.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    public CartItem findOneByProduct(Product product);
}
