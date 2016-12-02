package com.ddf.controller;

import com.ddf.domain.Cart;
import com.ddf.domain.CartItem;
import com.ddf.domain.Product;
import com.ddf.repository.CartItemRepository;
import com.ddf.repository.CartRepository;
import com.ddf.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

@Controller
public class CartController {

    private final HttpServletRequest request;

    private final ProductRepository productRepository;

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartController(
        HttpServletRequest request,
        ProductRepository productRepository,
        CartRepository cartRepository,
        CartItemRepository cartItemRepository
    ) {
        this.request = request;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @RequestMapping("/carrinho")
    public String index(Model model) {
        model.addAttribute("cart", this.getUserCart(this.request));
        return "cart";
    }

    @RequestMapping(value = "/cart/add/{code}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public HashMap add(@PathVariable String code) {

        Cart cart = this.getUserCart(this.request);
        Product product = productRepository.findOneByCode(code);

        // Find cart item by product
        CartItem cartItem;
        cartItem = cartItemRepository.findOneByProduct(product);

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

        // Update session cart
        request.getSession().setAttribute("cart", cart);

        // Json response
        HashMap<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }

    @RequestMapping(value = "/cart/remove/{code}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public HashMap remove(@PathVariable String code) throws Exception {

        Cart cart = this.getUserCart(this.request);
        Product product = productRepository.findOneByCode(code);

        // Find cart item by product
        CartItem cartItem;
        cartItem = cartItemRepository.findOneByProduct(product);

        // If product doesn't exist on cart, create a new cart item
        if (cartItem == null) {
            throw new Exception("Item do carrinho não encontrado");
        }

        // Remove item
        cart.getItems().remove(cartItem);
        this.cartItemRepository.delete(cartItem);

        // Update session cart
        request.getSession().setAttribute("cart", cart);

        // Json response
        HashMap<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }

    private Cart getUserCart(HttpServletRequest request) {
        // Get Cart from Session
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        // If not found cart on session
        if (cart == null) {
            // Create new cart
            cart = new Cart();
            cart.setDate(new Date());
            this.cartRepository.save(cart);
            // And store to Session.
            request.getSession().setAttribute("cart", cart);
        }

        // Return cart from database
        return this.cartRepository.findOne(cart.getId());
    }
}
