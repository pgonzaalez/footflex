package com.tienda.footflex_version_inicial.service;

import com.tienda.footflex_version_inicial.domain.Product;
import com.tienda.footflex_version_inicial.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CsvGenerator {


    public String generateUsersCsv(List<User> users) {
        final String CSV_HEADER = "ID,Name,Email,Address,Phone\n";
        StringBuilder csvContent = new StringBuilder();
        csvContent.append(CSV_HEADER);

        for (User employee : users) {
            csvContent.append(employee.getId()).append(",")
                    .append(employee.getName()).append(",")
                    .append(employee.getEmail()).append(",")
                    .append(employee.getAddress()).append(",")
                    .append(employee.getPhone()).append("\n");
        }



        return csvContent.toString();
    }

    public String generateProductsCsv(List<Product> products) {
        final String CSV_HEADER = "ID,Name,Description,Brand,SKU,Price,Stock,Image\n";
        StringBuilder csvContent = new StringBuilder();
        csvContent.append(CSV_HEADER);

        for (Product product : products) {
            csvContent.append(product.getId()).append(",")
                    .append(product.getName()).append(",")
                    .append(product.getDescription()).append(",")
                    .append(product.getBrand()).append(",")
                    .append(product.getSku()).append(",")
                    .append(product.getPrice()).append(",")
                    .append(product.getStock()).append(",")
                    .append(product.getImage()).append("\n");
        }



        return csvContent.toString();
    }
}
