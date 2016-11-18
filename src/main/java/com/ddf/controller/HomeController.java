package com.ddf.controller;

import com.ddf.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private final ProductRepository productRepository;

    @Autowired
    public HomeController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("products", productRepository.findByFeatured(true));
        return "home";
    }
}
