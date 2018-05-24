package com.ecommerce.ECommerce.Service;

import com.ecommerce.ECommerce.DTO.ProductDto;
import com.ecommerce.ECommerce.Model.Product;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProductServiceInterface {

    public List<Product> getProductList();

    List<Product> getProductById(String productId);

    String createProduct(ProductDto product);

    int getMerchantRating(String productRating);

    List<Product> getMerchantById(String productId);

    List<Product> getProductsByCategory(String productCategory);

    List<Product> getProductsSortByPrice();

    List<Product> getProductSortByRating();
}
