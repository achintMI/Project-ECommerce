package com.ecommerce.ECommerce.Service;

import com.ecommerce.ECommerce.ApiCall.SearchApiCall;
import com.ecommerce.ECommerce.DTO.ProductDto;
import com.ecommerce.ECommerce.DTO.ProductSearchDto;
import com.ecommerce.ECommerce.Model.Product;
import com.ecommerce.ECommerce.Repository.ProductRepositoryInterface;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public double getMerchantRating(String productId) {
        List<Product> product =  productRepository.findByProductId(productId);
        List<ProductDto> x;
        return product.get(0).getMerchantRating();
    }

    @Override
    public List<ProductDto> getProductList() {
        List<Product> allProducts = productRepository.findAll();
        List<ProductDto> productData = null;
        BeanUtils.copyProperties(allProducts, productData);
        return productData;
    }

    @Override
    public List<ProductDto> getProductById(String productId) {
        List<Product> productById = productRepository.findByProductId(productId);
        List<ProductDto> productDtoById = null;
        BeanUtils.copyProperties(productById, productDtoById);
        return productDtoById;
    }

    @Override
    public List<ProductDto> getMerchantById(String productId){
        List<Product> merchantByCategory = productRepository.findByProductId(productId);
        List<ProductDto> productDtoByCategory = null;
        BeanUtils.copyProperties(merchantByCategory, productDtoByCategory);
        return productDtoByCategory;
    }

    @Override
    public List<ProductDto> getProductsByCategory(String productCategory) {
        List<ProductDto> productByCategory = productRepository.findByProductCategory(productCategory);
        List<ProductDto> productDtoByCategory = null;
        BeanUtils.copyProperties(productByCategory, productDtoByCategory);
        return productDtoByCategory;
    }

    @Override
    public List<ProductDto> getProductsSortByPrice() {
        List<Product> productSortedByPrice = productRepository.findAll(new Sort(Sort.Direction.ASC, "productPrice"));
        List<ProductDto> productDtoSortedByPrice = null;
        BeanUtils.copyProperties(productSortedByPrice, productDtoSortedByPrice);
        return productDtoSortedByPrice;
    }

    @Override
    public List<ProductDto> getProductSortByRating() {
        List<Product> productSortedByRating = productRepository.findAll(new Sort(Sort.Direction.DESC, "productRating"));
        List<ProductDto> productDtoSortedByRating = null;
        BeanUtils.copyProperties(productSortedByRating, productDtoSortedByRating);
        return productDtoSortedByRating;
    }

    @Override
    public boolean reduceProductCount(String productId, int quantity) {
        int productQuantity = -quantity;
        UpdateResult result = mongoOperations.updateFirst(new Query(Criteria.where("_id").is(productId)),
                new Update().inc("unitStock", productQuantity), Product.class);
        if(result.isModifiedCountAvailable()){
            return true;
        }
        return false;
    }

    @Override
    public List<ProductDto> getMerchantByName(String productName) {
        List<Product> productsMerchantName = productRepository.findByProductName(productName);
        List<ProductDto> productDtobyMerchantName = null;
        BeanUtils.copyProperties(productsMerchantName, productDtobyMerchantName);
        return productDtobyMerchantName;
    }


}
