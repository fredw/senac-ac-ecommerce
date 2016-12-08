package com.ddf.controller;

import com.ddf.domain.Cart;
import com.ddf.domain.CartItem;
import com.ddf.domain.Product;
import com.ddf.service.CartItemService;
import com.ddf.service.CartUserService;
import com.ddf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class CartItemController {

    private final CartUserService cartUserService;

    private final CartItemService cartItemService;

    private final ProductService productService;

    @Autowired
    public CartItemController(
        CartUserService cartUserService,
        CartItemService cartItemService,
        ProductService productService
    ) {
        this.cartUserService = cartUserService;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @RequestMapping(value = "/cart/add/{code}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public HashMap add(HttpServletRequest request, @PathVariable String code) {

        Cart cart = this.cartUserService.getUserCart(request);
        Product product = this.productService.getByCode(code);

        this.cartItemService.add(cart, product);

        // Json response
        HashMap<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }

    @RequestMapping(value = "/cart/update/{code}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public HashMap update(@RequestBody CartItem cartItem, @PathVariable String code) throws Exception {

        Product product = this.productService.getByCode(code);

        this.cartItemService.update(cartItem, product);

        // Json response
        HashMap<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }

    @RequestMapping(value = "/cart/remove/{code}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public HashMap remove(HttpServletRequest request, @PathVariable String code) throws Exception {

        Cart cart = this.cartUserService.getUserCart(request);
        Product product = this.productService.getByCode(code);

        this.cartItemService.remove(cart, product);

        // Json response
        HashMap<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }
}
