package com.ddf.controller.panel;

import com.ddf.SenacAcEcommerceApplication;
import com.ddf.domain.Product;
import com.ddf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/painel/produtos")
public class PanelProductController {

    private final ProductService productService;

    @Autowired
    public PanelProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("products", this.productService.findAll());
        return "panel/product/index";
    }

    @RequestMapping(value = "/excluir", method = RequestMethod.POST)
    public String indexDelete(@RequestParam(value="code[]", required=false) List<String> codes) {
        codes.forEach(code -> {
            this.productService.delete(this.productService.getByCode(code));
        });
        return "redirect:/painel/produtos";
    }

    @RequestMapping("/novo")
    public String formNew(Model model) {
        model.addAttribute("product", new Product());
        return "panel/product/form";
    }

    @RequestMapping("/{code}")
    public String formDetail(@PathVariable String code, Model model) {
        model.addAttribute("product", this.productService.getByCode(code));
        return "panel/product/form";
    }

    @RequestMapping(value = {"/novo", "/{code}"}, method = RequestMethod.POST)
    public String formSave(
        @Valid Product product,
        BindingResult result,
        @RequestParam("image") MultipartFile image,
        @RequestParam("file") MultipartFile file,
        Model model
    ) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("product", product);
            return "panel/product/form";
        }

        /*if (!file.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path path = Paths.get(SenacAcEcommerceApplication.UPLOAD_PATH + fileName);
            Files.write(path, file.getBytes());
            product.setImage(fileName);
        }*/

        this.productService.save(product);
        return "redirect:/painel/produtos";
    }
}
