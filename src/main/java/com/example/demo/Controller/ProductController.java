package com.example.demo.Controller;

import com.example.demo.Model.Product;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    // Display a list of all products
    @GetMapping
    public String showProductList(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "/products/index";
    }

    @GetMapping("/admin")
    public String showProductListAdmin(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "/products/admin";
    }

    // For adding a new product
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories()); // Load categories
        return "/products/add-product";
    }

    // Process the form for adding a new product
    @PostMapping("/add")
    public String addProduct(@Valid Product product, BindingResult result, @RequestParam("imageFile") MultipartFile imageFile) {
        if (result.hasErrors()) {
            return "/products/add-product";
        }
        try {
            productService.addProduct(product, imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return "/products/add-product";
        }
        return "redirect:/products/admin";
    }

    // For editing a product
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories()); // Load categories
        return "/products/update-product";
    }

    // Process the form for updating a product
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @Valid Product product,
                                BindingResult result, @RequestParam("imageFile") MultipartFile imageFile) {
        if (result.hasErrors()) {
            product.setId(id); // set id to keep
            return "/products/update-product";
        }
        try {
            productService.updateProduct(product, imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return "/products/update-product";
        }
        return "redirect:/products/admin";
    }

    // Handle request to delete a product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/products/admin";
    }

    @GetMapping("/management")
    public String show() {
        return "/products/product-management";
    }
}
