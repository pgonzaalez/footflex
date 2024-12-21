/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.footflex_version_inicial.Controller;

import com.tienda.footflex_version_inicial.dao.ProductDAO;
import com.tienda.footflex_version_inicial.domain.Product;
import com.tienda.footflex_version_inicial.domain.User;
import com.tienda.footflex_version_inicial.service.ProductService;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AdminController {
    @Autowired
    ProductService productService;
    
    /**
     * Muestra la pagina de admin guardando el usuario con el que se inicia
     * sesion en el login
     *
     * @param model
     * @return pagina de admin
     */
    @GetMapping("/admin")
    public String showAdmin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        
        // Obtén los últimos 4 productos y agrégales al modelo
        List<Product> lastFourProducts = productService.getLastFourProducts();
        model.addAttribute("lastFourProducts", lastFourProducts);
        
        return "/admin/index";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "/admin/login";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "/admin/register";
    }
   

    @GetMapping("/charts")
    public String getPieChart(Model model) {

        Map<String, Integer> graphData = new TreeMap<>();

        List<String> brands = productService.findAllBrands();
        for (String brand : brands) {
            Long count = productService.getCountByBrand(brand);
            graphData.put(brand, Math.toIntExact(count));
        }

        model.addAttribute("chartData", graphData);

        return "/admin/charts";
    }

}
