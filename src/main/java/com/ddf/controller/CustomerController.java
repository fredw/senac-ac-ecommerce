package com.ddf.controller;

import com.ddf.domain.Customer;
import com.ddf.domain.Role;
import com.ddf.domain.User;
import com.ddf.service.CustomerService;
import com.ddf.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class CustomerController {

    private final CustomerService customerService;
    private final RoleService roleService;

    @Autowired
    public CustomerController(CustomerService customerService, RoleService roleService) {
        this.customerService = customerService;
        this.roleService = roleService;
    }

    @RequestMapping("/cadastro")
    public ModelAndView registerIndex() {
        User user = new User();
        user.setRole(this.roleService.get(Role.CUSTOMER));
        Customer customer = new Customer();
        customer.setUser(user);
        ModelAndView mv = new ModelAndView("customer/register");
        mv.addObject("customer", customer);
        return mv;
    }

    @RequestMapping(value = "/cadastro", method = RequestMethod.POST)
    public ModelAndView registerSubmit(
        Customer customer,
        BindingResult resultCustomer
        //,User user,
        //BindingResult resultUser
    ) {
        // # Customer
        //System.out.println(customer.getUser().getName());
        //System.out.println(customer.getUser().getEmail());
        //System.out.println(customer.getUser().getPassword());
        //System.out.println(customer.getUser().getRole().getId());
        // # User
        //System.out.println(user.getName());
        //System.out.println(user.getEmail());
        //System.out.println(user.getPassword());
        //System.out.println(user.getRole().getId());

        ModelAndView mv = new ModelAndView("customer/register");
        mv.addObject("customer", customer);

        if (resultCustomer.hasErrors()) {
        //if (resultCustomer.hasErrors() || resultUser.hasErrors()) {
            //System.out.println(resultCustomer);
            //System.out.println(resultUser);
            return mv;
        }

        try {
            this.customerService.save(customer);
        } catch (Exception e) {
            return mv;
        }

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
        Customer customer = this.customerService.get(1L);

        ModelAndView mv = new ModelAndView("customer/order/index");
        mv.addObject("orders", this.customerService.getOrders(customer));
        return mv;
    }

    @RequestMapping("/minhas-compras/{orderId}")
    public ModelAndView myOrders(@PathVariable Long orderId) throws Exception {

        // @TODO: alterar para retornar somente as compras do usuário logado
        Customer customer = this.customerService.get(1L);

        ModelAndView mv = new ModelAndView("customer/order/detail");
        mv.addObject("order", this.customerService.getOrder(customer, orderId));
        return mv;
    }
}
