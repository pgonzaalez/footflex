/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.footflex_version_inicial.Controller;

import com.tienda.footflex_version_inicial.dao.ProductDAO;
import com.tienda.footflex_version_inicial.domain.Product;
import com.tienda.footflex_version_inicial.domain.User;
import com.tienda.footflex_version_inicial.service.CsvGenerator;
import com.tienda.footflex_version_inicial.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class ProductController {
    
    @Autowired
    ProductService productService;

    @Autowired
    ProductDAO ProductDao;

    @Autowired
    private CsvGenerator csvGenerator;


    @GetMapping("/createProd")
    public String showCreate(Product product, Model model) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        model.addAttribute("username", username);
        
        return "/admin/create_product";
    }

    @GetMapping("/products")
    public String showProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);

        return "/admin/crud_products";
    }

    // csv generator

    @GetMapping("/products/csv")
    public ResponseEntity<byte[]> generateCsvFile() {
        List<Product> product = ProductDao.findAll();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "products.csv");

        byte[] csvBytes = csvGenerator.generateProductsCsv(product).getBytes();

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/update/{id}")
    public String update(Product product, Model model) {
        product = productService.findProduct(product);
        model.addAttribute("product", product);
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        
        return "/admin/edit_product";
    }

    @PostMapping("/products")
    public String submitForm(Product product, Model model, @RequestParam(name = "file", required = false) MultipartFile image) {
        // Save the person object to the database
        productService.saveProduct(product, image);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        
        return "/admin/product-delete-confirmation";
    }

    @GetMapping("/deleteOK/{id}")
    public String deleteProduct(Product product) {
        productService.deleteProduct(product.getId());
        return "redirect:/products";
    }
}
