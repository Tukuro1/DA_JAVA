package com.example.demo.Controller;


import com.example.demo.Model.CartItem;
import com.example.demo.Model.Order;
import com.example.demo.Model.Product;
import com.example.demo.Model.User;
import com.example.demo.Service.CartService;
import com.example.demo.Service.EmailService;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.UserService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private  UserService userService;
    @GetMapping("/checkout")
    public String checkout() {
        return "/cart/checkout";
    }
    @PostMapping("/submit")
    public String submitOrder(String customerName, String email,Principal principal) {
        System.out.println(principal.getName());
        List<CartItem> cartItems = cartService.getCartItems();
        String nameListProduct = "";
        String titleMail = "Chào " + customerName + "\n Cảm ơn đã đã mua hàng của chúng tôi";
        for (CartItem c : cartItems) {
            nameListProduct += c.getNameProduct();
        }
        if (cartItems.isEmpty()) {
            return "redirect:/cart"; // Redirect if cart is empty
        }
        try {
            orderService.createOrder(customerName, cartItems);
            emailService.sendEmail(titleMail, nameListProduct, email);
        } catch (Exception e) {
            return "redirect:/cart";
        }

        return "redirect:/order/confirmation";
    }
    @GetMapping("/confirmation")
    public String orderConfirmation(Model model) {
        model.addAttribute("message", "Your order has been successfully placed.");
        return "cart/order-confirmation";
    }


}