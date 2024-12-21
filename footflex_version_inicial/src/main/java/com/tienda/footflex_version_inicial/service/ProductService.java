/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.footflex_version_inicial.service;

import com.tienda.footflex_version_inicial.domain.Product;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


public interface ProductService {
    Product saveProduct(Product product, @RequestParam(name = "file", required = false) MultipartFile image);
    List<Product> getAllProducts();
    Product findProduct(Product product);
    Product getProductById(Long id);
    void deleteProduct(Long id);
    Long getCountByBrand(String brand);
    List<String> findAllBrands();
    int getCountBrands();
    List<Product> getLastFourProducts();
}
