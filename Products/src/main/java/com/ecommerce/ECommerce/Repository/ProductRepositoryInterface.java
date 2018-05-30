package com.ecommerce.ECommerce.Repository;

import com.ecommerce.ECommerce.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepositoryInterface extends MongoRepository<Product, String> {
    List<Product> findByProductId(String productId);

    List<Product> findByProductCategory(String productId);

    /**
     * Remove this if not used
     */
    List<Product> findByProductPrice();

    List<Product> findByProductName(String productName);
}
