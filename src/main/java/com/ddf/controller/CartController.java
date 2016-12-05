package com.ddf.controller;

import com.ddf.domain.*;
import com.ddf.repository.*;
import com.ddf.service.CartItemService;
import com.ddf.service.CartService;
import com.ddf.service.CartUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class CartController {

    private final CartUserService cartUserService;

    private final CartService cartService;

    private final CustomerRepository customerRepository;

    @Autowired
    public CartController(
        CartUserService cartUserService,
        CartService cartService,
        CustomerRepository customerRepository
    ) {
        this.cartUserService = cartUserService;
        this.cartService = cartService;
        this.customerRepository = customerRepository;
    }

    @RequestMapping("/carrinho")
    public String index(HttpServletRequest request, Model model) {
        model.addAttribute("cart", this.cartUserService.getUserCart(request));
        return "cart/index";
    }

    @RequestMapping(value = "/cart/submit", method = RequestMethod.POST)
    public ModelAndView submit(HttpServletRequest request) {

        Cart cart = this.cartUserService.getUserCart(request);

        // @TODO: pegar o cliente cadastrado
        Customer customer = customerRepository.findOne(1L);

        this.cartService.submit(cart, customer);

        // Reset cart
        request.getSession().setAttribute("cart", null);

        return new ModelAndView("order/success");
    }
}
