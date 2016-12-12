package com.ddf.controller;

import com.ddf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private final ProductService productService;

    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/")
    public String index(Model model) {

        List<String> highlights = new ArrayList<>();
        highlights.add(0, "Anúncio 1");
        highlights.add(1, "Anúncio 2");
        highlights.add(2, "Anúncio 3");

        model.addAttribute("highlights", highlights);
        model.addAttribute("products", this.productService.findByFeatured(true));
        return "home";
    }
}
