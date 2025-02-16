package org.inventoryservice.controller;


import org.inventoryservice.entity.Product;
import org.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class InverntoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("")
    public List<Product> getAll() {
        return inventoryService.getAllProducts();
    }

    @PostMapping("/save")
    public Product save(@RequestBody Product product) {
        return inventoryService.addProduct(product);
    }

    @GetMapping("/findById/{id}")
    public Product findById(@PathVariable Integer id) {
        return inventoryService.getProductById(id);
    }

    @PostMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        inventoryService.deleteProduct(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateUser(@RequestBody Product product, @PathVariable Integer id) {
        Product product1 = inventoryService.updateProduct(product, id);
        return ResponseEntity.ok(product1);
    }



}
