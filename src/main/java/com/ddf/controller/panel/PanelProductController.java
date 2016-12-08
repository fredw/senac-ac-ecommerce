package com.ddf.controller.panel;

import com.ddf.SenacAcEcommerceApplication;
import com.ddf.domain.Product;
import com.ddf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("panel/product/index");
        mv.addObject("products", this.productService.findAll());
        return mv;
    }

    @RequestMapping(value = "/excluir", method = RequestMethod.POST)
    public ModelAndView indexDelete(@RequestParam(value="code[]", required=false) List<String> codes) {
        codes.forEach(code -> {
            this.productService.delete(this.productService.getByCode(code));
        });
        return new ModelAndView("redirect:/painel/produtos");
    }

    @RequestMapping("/novo")
    public ModelAndView formNew() {
        ModelAndView mv = new ModelAndView("panel/product/form");
        mv.addObject("product", new Product());
        return mv;
    }

    @RequestMapping("/{code}")
    public ModelAndView formDetail(@PathVariable String code) {
        ModelAndView mv = new ModelAndView("panel/product/form");
        mv.addObject("product", this.productService.getByCode(code));
        return mv;
    }

    @RequestMapping(value = {"/novo", "/{code}"}, method = RequestMethod.POST)
    public ModelAndView formSave(
        @Valid Product product,
        BindingResult result,
        @RequestParam("image") MultipartFile image,
        @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("panel/product/form");
            mv.addObject("product", product);
            return mv;
        }

        /*if (!file.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path path = Paths.get(SenacAcEcommerceApplication.UPLOAD_PATH + fileName);
            Files.write(path, file.getBytes());
            product.setImage(fileName);
        }*/

        this.productService.save(product);
        return new ModelAndView("redirect:/painel/produtos");
    }
}
