
package com.tienda.footflex_version_inicial.service;

import com.tienda.footflex_version_inicial.dao.ProductDAO;
import com.tienda.footflex_version_inicial.domain.Product;
import jakarta.persistence.criteria.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    /**
     * Para guardar el producto guarda una imagen en una carpeta del proyecto en
     * reosurces además de guardar la fecha y hora de cuando se crea el producto
     *
     * @param product
     * @param image
     * @return El producto guardado
     */
    @Override
    public Product saveProduct(Product product, MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            String ruta = "src/main/resources/static/img/products";

            try {
                byte[] bytes = image.getBytes();
                java.nio.file.Path rutaAbsoluta = Paths.get(ruta + "//" + image.getOriginalFilename());
                Files.write(rutaAbsoluta, bytes);
                product.setImage(image.getOriginalFilename());

            } catch (Exception ex) {
                // Maneja la excepción de manera apropiada
            }
        }

        // Establece la fecha de creación antes de guardar
        product.setCreatedAt(LocalDateTime.now());

        return productDAO.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    @Override
    public Product findProduct(Product product) {
        return productDAO.findById(product.getId()).orElse(null);
    }

    @Override
    public Product getProductById(Long id) {
        return productDAO.findById(id).orElse(null);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productDAO.findById(id).orElse(null);
        productDAO.delete(product);
    }

    @Override
    public Long getCountByBrand(String brand) {
        return productDAO.getCountByBrand(brand);
    }

    @Override
    public List<String> findAllBrands() {
        return productDAO.findAllBrands();
    }

    @Override
    public int getCountBrands() {
        return productDAO.getCountBrands();
    }
    
    @Override    
    public List<Product> getLastFourProducts() {
        return productDAO.findTop4ByOrderByCreatedAtDesc();
    }
}
