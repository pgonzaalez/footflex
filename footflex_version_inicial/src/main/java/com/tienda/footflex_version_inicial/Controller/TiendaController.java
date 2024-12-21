/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.footflex_version_inicial.Controller;

import com.tienda.footflex_version_inicial.domain.Product;
import com.tienda.footflex_version_inicial.service.ProductService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.tienda.footflex_version_inicial.domain.Carrito;
import com.tienda.footflex_version_inicial.domain.User;
import com.tienda.footflex_version_inicial.service.CarritoService;
import com.tienda.footflex_version_inicial.service.UserService;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class TiendaController {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @GetMapping("/tienda")
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);

        // Agrega el nombre del rol al modelo
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? "" : authorities.iterator().next().getAuthority();
        model.addAttribute("role", role);

        return "/tienda/Home";
    }

    // Endpoint para mostrar los detalles de un producto por su ID
    @GetMapping("/product-details/{id}")
    public String showProductDetails(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        // Obtén los últimos 4 productos y agrégales al modelo
        List<Product> lastFourProducts = productService.getLastFourProducts();
        model.addAttribute("lastFourProducts", lastFourProducts);

        return "/tienda/product-details";
    }
    
    @GetMapping("/profile")
    public String showProfile() {
        return "/tienda/user_profile";
    }

}
