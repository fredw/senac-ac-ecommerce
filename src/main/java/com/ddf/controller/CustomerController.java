package com.ddf.controller;

import com.ddf.domain.Customer;
import com.ddf.domain.User;
import com.ddf.repository.CustomerRepository;
import com.ddf.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
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
    public ModelAndView registerSubmit(
        @ModelAttribute("customer") @Valid Customer customer,
        BindingResult resultCustomer,
        @ModelAttribute("user") @Valid User user,
        BindingResult resultUser
    ) {
        customer.setUser(user);

        if (resultCustomer.hasErrors() || resultUser.hasErrors()) {
            System.out.println(resultCustomer);
            System.out.println(resultUser);
            //return new ModelAndView("customer/register");
            ModelAndView mv = new ModelAndView("customer/register");
            mv.addObject("customer", customer);
            mv.addObject("user", user);
            //mv.addObject("errors", resultUser);
            return mv;
        }
        this.customerService.save(customer);
        return new ModelAndView("customer/success");
    }

    @RequestMapping("/login")
    public ModelAndView login() {

        ModelAndView mv = new ModelAndView("customer/login");

        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            mv.addObject("user", user);
        } catch (Exception ignored) {}

        return mv;
    }

    @RequestMapping("/minhas-compras")
    public ModelAndView myOrders() {

        // @TODO: alterar para retornar somente as compras do usuário logado
        Customer customer = this.customerRepository.findOne(1L);

        ModelAndView mv = new ModelAndView("customer/order/index");
        mv.addObject("orders", this.customerService.getOrders(customer));
        return mv;
    }

    @RequestMapping("/minhas-compras/{orderId}")
    public ModelAndView myOrders(@PathVariable Long orderId) throws Exception {

        // @TODO: alterar para retornar somente as compras do usuário logado
        Customer customer = this.customerRepository.findOne(1L);

        ModelAndView mv = new ModelAndView("customer/order/detail");
        mv.addObject("order", this.customerService.getOrder(customer, orderId));
        return mv;
    }
}
