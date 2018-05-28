package com.ecommerce.ECommerce.Service;

import com.ecommerce.ECommerce.ApiCall.SearchApiCall;
import com.ecommerce.ECommerce.DTO.ProductDto;
import com.ecommerce.ECommerce.DTO.ProductSearchDto;
import com.ecommerce.ECommerce.Model.Product;
import com.ecommerce.ECommerce.Repository.ProductRepositoryInterface;
import com.mongodb.MongoClient;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl  implements ProductServiceInterface{

    @Autowired
    private ProductRepositoryInterface  productRepository;


    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    @Qualifier("searchapi")
    public SearchApiCall productsApiCall;

    @Override
    public String createProduct(ProductDto product){
        Product productClass = new Product();
        BeanUtils.copyProperties(product, productClass);
        productRepository.save(productClass);
        ProductSearchDto productSearchDto = new ProductSearchDto(product);
        productsApiCall.addNewProduct(productSearchDto);
        return "Success";
    }

    @Override
    public int getMerchantRating(String productId) {
        List<Product> product =  productRepository.findByProductId(productId);
        return product.get(0).getMerchantRating();
    }

    @Override
    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductById(String productId) {
        return productRepository.findByProductId(productId);
    }

    @Override
    public List<Product> getMerchantById(String productId){
        return productRepository.findByProductCategory(productId);
    }

    @Override
    public List<Product> getProductsByCategory(String productCategory) {
        return productRepository.findByProductCategory(productCategory);
    }

    @Override
    public List<Product> getProductsSortByPrice() {
        return productRepository.findAll(new Sort(Sort.Direction.ASC, "productPrice"));
    }

    @Override
    public List<Product> getProductSortByRating() {
        return productRepository.findAll(new Sort(Sort.Direction.DESC, "productRating"));
    }

    @Override
    public boolean reduceProductCount(String productId) {
        UpdateResult result = mongoOperations.updateFirst(new Query(Criteria.where("_id").is(productId)),
                new Update().inc("unitStock", -1), Product.class);
        if(result.isModifiedCountAvailable()){
            return true;
        }
        return false;

    }


}
