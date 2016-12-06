package com.ddf.service;

import com.ddf.domain.Cart;
import com.ddf.domain.CartStatus;
import com.ddf.repository.CartRepository;
import com.ddf.repository.CartStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class CartUserService {

    private final CartRepository cartRepository;

    private final CartStatusRepository cartStatusRepository;

    @Autowired
    public CartUserService(CartRepository cartRepository, CartStatusRepository cartStatusRepository) {
        this.cartRepository = cartRepository;
        this.cartStatusRepository = cartStatusRepository;
    }

    public Cart getUserCart(HttpServletRequest request) {
        Long cartId = (Long) request.getSession().getAttribute("cart");
        Cart cart = cartId != null ? this.cartRepository.findOne(cartId) : null;

        if (cart == null) {
            // Create new cart
            cart = new Cart();
            cart.setDate(new Date());
            cart.setStatus(this.cartStatusRepository.findOne(CartStatus.ACTIVE));
            this.cartRepository.save(cart);
            // And store to Session
            request.getSession().setAttribute("cart", cart.getId());
        }

        return cart;
    }
}
