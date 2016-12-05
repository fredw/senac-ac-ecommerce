package com.ddf.controller;

import com.ddf.domain.Customer;
import com.ddf.domain.User;
import com.ddf.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping("/cadastro")
    public ModelAndView registerIndex() {
        ModelAndView mv = new ModelAndView("customer/register");
        Customer customer = new Customer();
        customer.setUser(new User());
        mv.addObject("customer", customer);
        return mv;
    }

    @RequestMapping(value = "/cadastro", method = RequestMethod.POST)
    public ModelAndView registerSubmit(@Valid Customer customer, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("customer/form");
        }
        this.customerService.save(customer);
        return new ModelAndView("customer/success");
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("customer/login");
        return mv;
    }
}
