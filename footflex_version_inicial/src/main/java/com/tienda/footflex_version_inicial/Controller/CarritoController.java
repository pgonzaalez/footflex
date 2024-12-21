/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.footflex_version_inicial.Controller;

import com.tienda.footflex_version_inicial.domain.Carrito;
import com.tienda.footflex_version_inicial.domain.Product;
import com.tienda.footflex_version_inicial.domain.User;
import com.tienda.footflex_version_inicial.service.CarritoService;
import com.tienda.footflex_version_inicial.service.ProductService;
import com.tienda.footflex_version_inicial.service.UserService;
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


@Controller
public class CarritoController {
    
    @Autowired
    CarritoService carritoService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    ProductService productService;
    
    
    @GetMapping("/carrito")
    public String showProducts(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        Long userid = getUserId(authentication);

        // Obtener productos del carrito del usuario actual
        List<Carrito> carritoItems = carritoService.getCarritoItemsByuserid(userid);
        
        // Calcular subtotal sumando las cantidades de todos los productos en el carrito
        double subtotal = carritoItems.stream().mapToDouble(Carrito::getTotal).sum();
        
        
        model.addAttribute("carritoItems", carritoItems);
        model.addAttribute("subtotal", subtotal);
        
        return "/tienda/carrito";
    }
    
    
    
    @PostMapping("/cart")
    public String addCart(@RequestParam Long id, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Carrito carrito = new Carrito();
        Product producto = productService.getProductById(id);
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        carrito.setProduct_id(id);
        carrito.setUserid(user.getId());
        carrito.setQuantity(1);
        carrito.setTotal(producto.getPrice());

        carritoService.saveCarrito(carrito);
        return "redirect:/tienda";
    }

    @GetMapping("/delete_carrito/{id}")
    public String DeleteCarritoConfirmation(@PathVariable Long id, Model model) {
        Carrito carritoItems = carritoService.getCarritoById(id);
        model.addAttribute("carritoItems", carritoItems);
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        
        return "/tienda/carrito-delete-confirmation";
    }
    
    @GetMapping("/delete_carrito_OK/{id}")
    public String deleteCarrito(Carrito carrito) {
        carritoService.deleteCarrito(carrito.getId());
        return "redirect:/carrito";
    }
    
    
    
    //Funcion auxiliar para obtener id por nombre de usuario
    private Long getUserId(Authentication authentication) {
    // Obtiene el nombre de usuario del objeto de autenticación
    String username = authentication.getName();
    
    // Utiliza el servicio de usuario para obtener el usuario por nombre de usuario
    User user = userService.getUserByUsername(username);

    // Devuelve el ID del usuario
    return user.getId();
}
    
}
