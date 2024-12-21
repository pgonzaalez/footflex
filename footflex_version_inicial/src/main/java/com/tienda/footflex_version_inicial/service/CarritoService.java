/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.footflex_version_inicial.service;

import com.tienda.footflex_version_inicial.domain.Carrito;
import java.util.List;


public interface CarritoService {
    List<Carrito> getAllCarrito();
    List<Carrito> getCarritoItemsByuserid(Long userid);
    Carrito getCarritoById(Long id);
    Carrito saveCarrito(Carrito carrito);
    void deleteCarrito(Long id);
}
