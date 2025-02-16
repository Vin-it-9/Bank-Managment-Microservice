package org.inventoryservice.service;

import jakarta.transaction.Transactional;
import org.inventoryservice.ProductNotFoundException;
import org.inventoryservice.entity.Product;
import org.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class InventoryService {

    @Autowired
    private
    InventoryRepository inventoryRepository;

    public Product addProduct(Product product) {
        return inventoryRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return inventoryRepository.findAll();
    }

    public Product getProductById(Integer id) {
        Product product = inventoryRepository.findById(id).get();
        return product;
    }


    public Product updateProduct(Product product, Integer id) {

        Product existinProduct = inventoryRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        existinProduct.setName(product.getName());
        existinProduct.setPrice(product.getPrice());
        existinProduct.setQuantity(product.getQuantity());
        existinProduct.setDescription(product.getDescription());

       return inventoryRepository.save(existinProduct);

    }


    public void deleteProduct(Integer id) {
        inventoryRepository.deleteById(id);
    }

    public Product addStock(Product product , int quantity) {
        return inventoryRepository.save(product);
    }






}
