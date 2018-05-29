package com.ecommerce.ECommerce.Controller;


import com.ecommerce.ECommerce.DTO.ProductDto;
import com.ecommerce.ECommerce.Exception.ProductException;
import com.ecommerce.ECommerce.Model.Product;
import com.ecommerce.ECommerce.Service.ProductServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.List;

@RestController
@RequestMapping("products")
public class Productcontroller {

    @Autowired
    private ProductServiceInterface productService;

    @RequestMapping(value = "/createProduct", method = RequestMethod.POST)
    public String createProduct(@RequestBody ProductDto product){
        return productService.createProduct(product);
    }

    @RequestMapping("/getAllProducts")
    public List<ProductDto> getAllProducts() throws ProductException {
        List<ProductDto> products = productService.getProductList();
        if(null == products){
            throw new ProductException("Data not Available");
        }
        return products;
    }

    @RequestMapping("/getProductById/{productId}")
    public List<ProductDto> getProductById(@PathVariable(value = "productId") String productId) throws ProductException {
        List<ProductDto> product = productService.getProductById(productId);
        if(null == product){
            throw new ProductException("Data not Available");
        }
        return product;
    }

    @RequestMapping("/getMerchantRating")
    public double getMerchantRating(@PathVariable(value = "productId") String productId){
        return productService.getMerchantRating(productId);
    }

    @RequestMapping("/get-product-by-category")
    public List<ProductDto> getProductsByCategory(@RequestParam String productCategory) throws ProductException {
        List<ProductDto> products = productService.getProductsByCategory(productCategory);
        if(null == products){
            throw new ProductException("Data not Available");
        }
        return  products;
    }

    @RequestMapping("/getProductSortByPrice")
    public List<ProductDto> getProductSortByPrice() throws ProductException {
        List<ProductDto> products = productService.getProductsSortByPrice();
        if(null == products){
            throw new ProductException("Data not Available");
        }
        return products;
    }

    @RequestMapping("/getProductSortByRating")
    public List<ProductDto> getProductSortByRating() throws ProductException {
        List<ProductDto> products = productService.getProductSortByRating();
        if(null == products){
            throw new ProductException("Data not Available");
        }
        return products;
    }

    @RequestMapping("/reduceProductCount")
    public boolean reduceProductCount(@RequestParam String productId, int quantity) throws ProductException {
        boolean isReduced = productService.reduceProductCount(productId, quantity);
        if(!isReduced){
            throw new ProductException("Data not Available");
        }
        return true;
    }

    @RequestMapping(value = "/get-product-by-name")
    public List<ProductDto> reduceProductCount(@RequestParam String productName) throws ProductException {
        String pname = URLDecoder.decode(productName);
        List<ProductDto> products = productService.getMerchantByName(productName);
        if(null == products){
            throw new ProductException("Data not Available");
        }
        return products;
    }
}
