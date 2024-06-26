package com.example.demo.Controller;

import com.example.demo.Model.Product;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")

    public class APIController {
        @Autowired
        private ProductService productService;


        @GetMapping("")
        public String showProductList(Model model) {
            model.addAttribute("products", productService.getAllProducts());
            return "/front-end/front-end";
        }
    }

