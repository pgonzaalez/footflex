/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.footflex_version_inicial.dao;

import com.tienda.footflex_version_inicial.domain.Carrito;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarritoDAO extends JpaRepository<Carrito, Long> {
    List<Carrito> findByuserid(Long userid);
}


