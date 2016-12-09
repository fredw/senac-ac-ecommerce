package com.ddf.controller;

import com.ddf.domain.Customer;
import com.ddf.domain.Role;
import com.ddf.domain.User;
import com.ddf.service.CustomerService;
import com.ddf.service.RoleService;
import com.ddf.service.UserService;
import com.ddf.service.exception.UserEmailDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class CustomerController {

    private final CustomerService customerService;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public CustomerController(
        CustomerService customerService,
        UserService userService,
        RoleService roleService
    ) {
        this.customerService = customerService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping("/cadastro")
    public String registerIndex(Model model) {
        model.addAttribute("role", this.roleService.get(Role.CUSTOMER));
        model.addAttribute("user", new User());
        return "customer/register";
    }

    @RequestMapping(value = "/cadastro", method = RequestMethod.POST)
    public String registerSubmit(
        HttpServletRequest request,
        @RequestParam(value = "redirect", required = false) String redirect,
        @Valid User user,
        BindingResult result,
        Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("role", this.roleService.get(Role.CUSTOMER));
            model.addAttribute("user", user);
            return "customer/register";
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        try {
            // Create the user
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            this.userService.save(user);
            // Create the customer
            Customer customer = new Customer();
            customer.setUser(user);
            this.customerService.save(customer);
        } catch (UserEmailDuplicatedException ex) {
            model.addAttribute("error", "E-mail already exists");
            return "customer/register";
        }

        // To be able to redirect to other page after register, is necessary to set user looged manualy (but I don't know how to do this)
        /*if (redirect != null) {
            return "redirect:" + redirect;
        }*/

        return "customer/success";
    }

    @RequestMapping("/login")
    public String login(Model model, HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        request.getSession().setAttribute("redirect", request.getParameter("redirect"));
        if (userDetails != null) {
            model.addAttribute("user", this.userService.getByUserDetail(userDetails));
        }
        return "customer/login";
    }

    @RequestMapping("/minhas-compras")
    public String myOrders(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Get logged customer
        User user = this.userService.getByUserDetail(userDetails);
        Customer customer = this.customerService.getByUser(user);

        model.addAttribute("orders", this.customerService.getOrders(customer));
        return "customer/order/index";
    }

    @RequestMapping("/minhas-compras/{orderId}")
    public String myOrders(@PathVariable Long orderId, Model model, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        // Get logged customer
        User user = this.userService.getByUserDetail(userDetails);
        Customer customer = this.customerService.getByUser(user);

        model.addAttribute("order", this.customerService.getOrder(customer, orderId));
        return "customer/order/detail";
    }
}
