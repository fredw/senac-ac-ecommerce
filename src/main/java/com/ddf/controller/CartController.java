package com.ddf.controller;

import com.ddf.domain.*;
import com.ddf.service.CartService;
import com.ddf.service.CartUserService;
import com.ddf.service.CustomerService;
import com.ddf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CartController {

    private final CartUserService cartUserService;
    private final CartService cartService;
    private final UserService userService;
    private final CustomerService customerService;

    @Autowired
    public CartController(
        CartUserService cartUserService,
        CartService cartService,
        UserService userService,
        CustomerService customerService
    ) {
        this.cartUserService = cartUserService;
        this.cartService = cartService;
        this.userService = userService;
        this.customerService = customerService;
    }

    @RequestMapping("/carrinho")
    public String index(HttpServletRequest request, Model model) {
        model.addAttribute("cart", this.cartUserService.getUserCart(request));
        return "cart/index";
    }

    @RequestMapping("/carrinho/identificacao")
    public String identification() {
        return "cart/identification";
    }

    @RequestMapping(value = "/cart/submit", method = RequestMethod.POST)
    public String submit(HttpServletRequest request, @AuthenticationPrincipal final UserDetails userDetails) {

        if (userDetails == null) {
            return "redirect:/carrinho/identificacao";
        }

        // Get logged customer
        User user = this.userService.getByUserDetail(userDetails);
        Customer customer = this.customerService.getByUser(user);

        Cart cart = this.cartUserService.getUserCart(request);
        this.cartService.submit(cart, customer);

        // Reset session cart
        request.getSession().setAttribute("cart", null);

        return "order/success";
    }
}
