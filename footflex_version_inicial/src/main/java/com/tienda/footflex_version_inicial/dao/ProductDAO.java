/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.footflex_version_inicial.dao;

import com.tienda.footflex_version_inicial.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProductDAO extends JpaRepository<Product, Long>{

    List<Product> findTop4ByOrderByCreatedAtDesc();
    @Query(value = "SELECT COUNT(*) FROM products WHERE brand = ?1", nativeQuery = true)
    Long getCountByBrand(String brand);

    @Query(value="SELECT DISTINCT brand FROM products",
            nativeQuery=true)
    List<String> findAllBrands();

    @Query(value = "SELECT COUNT(DISTINCT brand) FROM products", nativeQuery = true)
    int getCountBrands();
    
}
