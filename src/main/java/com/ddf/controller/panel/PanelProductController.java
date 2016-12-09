package com.ddf.controller.panel;

import com.ddf.Application;
import com.ddf.domain.Product;
import com.ddf.service.ProductService;
import com.ddf.service.exception.ProductCodeDuplicatedException;
import com.ddf.service.exception.ProductFileNotNullException;
import com.ddf.service.exception.ProductImageInvalidException;
import com.ddf.service.exception.ProductImageNotNullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String indexDelete(
        @RequestParam(value="code[]", required=false) List<String> codes,
        RedirectAttributes redirectAttributes
    ) {
        codes.forEach(code -> this.productService.delete(this.productService.getByCode(code)));
        redirectAttributes.addAttribute("message", "Produtos excluídos com sucesso!");
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
        @RequestParam("imageFile") MultipartFile image,
        @RequestParam("fileFile") MultipartFile file,
        Model model,
        RedirectAttributes redirectAttributes
    ) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("product", product);
            return "panel/product/form";
        }

        try {
            String fileName = "";
            String imageName = "";

            if (!file.isEmpty()) {
                fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                product.setFile(fileName);
            }

            if (!image.isEmpty()) {
                if (!image.getContentType().equals("image/jpeg")) {
                    throw new ProductImageInvalidException("Image file need to be a image/jpeg");
                }
                imageName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                product.setImage(imageName);
            }

            this.productService.save(product);

            // Save files
            if (!file.isEmpty() && !fileName.isEmpty()) {
                Path filePath = Paths.get(Application.UPLOAD_PATH + fileName);
                Files.write(filePath, file.getBytes());
            }
            if (!image.isEmpty() && !imageName.isEmpty()) {
                Path imagePath = Paths.get(Application.UPLOAD_PATH + imageName);
                Files.write(imagePath, image.getBytes());
            }

        } catch (ProductCodeDuplicatedException | ProductImageInvalidException | ProductImageNotNullException | ProductFileNotNullException ex) {
            model.addAttribute("error", ex.getMessage());
            return "panel/product/form";
        }

        redirectAttributes.addFlashAttribute("message", "Produto salvo com sucesso!");
        return "redirect:/painel/produtos";
    }
}
