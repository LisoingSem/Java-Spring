package com.example.productmenegement.controller;

import com.example.productmenegement.model.Product; // Make sure the class name is capitalized
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Endpoint for adding a new Product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        String insertQuery = "INSERT INTO products (id, name, price_per_unit, active_for_sell) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insertQuery, product.getId(), product.getName(), product.getPricePerUnit(), product.isActiveForSell());
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // Endpoint for finding a Product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        String selectQuery = "SELECT * FROM products WHERE id = ?";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(selectQuery, id);
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Map<String, Object> row = result.get(0);
        Product product = new Product();
        product.setId((int) row.get("id"));
        product.setName((String) row.get("name"));
        product.setPricePerUnit((double) row.get("price_per_unit"));
        product.setActiveForSell((boolean) row.get("active_for_sell"));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // Endpoint for getting all Products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        String selectQuery = "SELECT * FROM products";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(selectQuery);
        List<Product> products = new ArrayList<>();
        for (Map<String, Object> row : result) {
            Product product = new Product();
            product.setId((int) row.get("id"));
            product.setName((String) row.get("name"));
            product.setPricePerUnit((double) row.get("price_per_unit"));
            product.setActiveForSell((boolean) row.get("active_for_sell"));
            products.add(product);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Endpoint for updating a Product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
        String updateQuery = "UPDATE products SET name = ?, price_per_unit = ?, active_for_sell = ? WHERE id = ?";
        int rowsUpdated = jdbcTemplate.update(updateQuery, product.getName(), product.getPricePerUnit(), product.isActiveForSell(), id);
        if (rowsUpdated == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        product.setId(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}