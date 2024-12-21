/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.footflex_version_inicial.service;

import com.tienda.footflex_version_inicial.dao.CarritoDAO;
import com.tienda.footflex_version_inicial.domain.Carrito;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    CarritoDAO carritoDAO;

    @Override
    public List<Carrito> getAllCarrito() {
        return carritoDAO.findAll();
    }

    @Override
    public void deleteCarrito(Long id) {
        Carrito carrito = carritoDAO.findById(id).orElse(null);
        carritoDAO.delete(carrito);
    }

    @Override
    public Carrito saveCarrito(Carrito carrito) {
        return carritoDAO.save(carrito);
    }

    @Override
    public List<Carrito> getCarritoItemsByuserid(Long userid) {
        return carritoDAO.findByuserid(userid);
    }

    @Override
    public Carrito getCarritoById(Long id) {
        return carritoDAO.findById(id).orElse(null);
    }

}
