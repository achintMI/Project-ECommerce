package com.ecommerce.ECommerce.Service;

import com.ecommerce.ECommerce.DTO.ProductDto;

import java.util.List;

public interface ProductServiceInterface {

    public List<ProductDto> getProductList();

    List<ProductDto> getProductById(String productId);

    String createProduct(ProductDto product);

    double getMerchantRating(String productRating);

    List<ProductDto> getMerchantById(String productId);

    List<ProductDto> getProductsByCategory(String productCategory);

    List<ProductDto> getProductsSortByPrice();

    List<ProductDto> getProductSortByRating();

    boolean reduceProductCount(String productId, int quantity);

    List<ProductDto> getMerchantByName(String productName);
}
