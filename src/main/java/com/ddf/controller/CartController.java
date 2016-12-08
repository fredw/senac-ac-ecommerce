package com.ddf.controller;

import com.ddf.domain.*;
import com.ddf.service.CartService;
import com.ddf.service.CartUserService;
import com.ddf.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CartController {

    private final CartUserService cartUserService;
    private final CartService cartService;
    private final CustomerService customerService;

    @Autowired
    public CartController(
        CartUserService cartUserService,
        CartService cartService,
        CustomerService customerService
    ) {
        this.cartUserService = cartUserService;
        this.cartService = cartService;
        this.customerService = customerService;
    }

    @RequestMapping("/carrinho")
    public String index(HttpServletRequest request, Model model) {
        model.addAttribute("cart", this.cartUserService.getUserCart(request));
        return "cart/index";
    }

    @RequestMapping(value = "/cart/submit", method = RequestMethod.POST)
    public ModelAndView submit(HttpServletRequest request) {

        Cart cart = this.cartUserService.getUserCart(request);

        // @TODO: pegar o cliente cadastrado/logado
        Customer customer = customerService.get(1L);

        this.cartService.submit(cart, customer);

        // Reset cart
        request.getSession().setAttribute("cart", null);

        return new ModelAndView("order/success");
    }
}
