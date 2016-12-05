package com.ddf.service;

import com.ddf.domain.*;
import com.ddf.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartItemService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem add(Cart cart, Product product) {

        // Find cart item by product
        CartItem cartItem;
        cartItem = this.cartItemRepository.findOneByProduct(product);

        // If product doesn't exist on cart, create a new cart item
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setPrice(product.getPrice());
            cart.getItems().add(cartItem);
            this.cartRepository.save(cart);
        // If product already exist on cart, increase the quantity
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            this.cartItemRepository.save(cartItem);
        }

        return cartItem;
    }

    public CartItem update(CartItem c, Product product) throws Exception {
        // Find cart item by product
        CartItem cartItem = cartItemRepository.findOneByProduct(product);

        // If product doesn't exist on cart, create a new cart item
        if (cartItem == null) {
            throw new Exception("Item do carrinho não encontrado");
        }

        cartItem.setQuantity(c.getQuantity());
        this.cartItemRepository.save(cartItem);

        return cartItem;
    }

    public void remove(Cart cart, Product product) throws Exception {

        // Find cart item by product
        CartItem cartItem = cartItemRepository.findOneByProduct(product);

        // If product doesn't exist on cart, create a new cart item
        if (cartItem == null) {
            throw new Exception("Item do carrinho não encontrado");
        }

        // Remove item
        cart.getItems().remove(cartItem);
        this.cartItemRepository.delete(cartItem);
    }
}
