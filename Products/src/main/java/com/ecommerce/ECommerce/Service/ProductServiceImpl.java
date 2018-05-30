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
/**
 * Try to use Logger for logging
 */
public class ProductServiceImpl implements ProductServiceInterface {

  private static final String PRODUCT_PRICE = "productPrice";
  private static final String PRODUCT_RATING = "productRating";
  @Autowired
  private ProductRepositoryInterface productRepository;


  @Autowired
  MongoOperations mongoOperations;

  @Autowired
  @Qualifier("searchapi")
  public SearchApiCall productsApiCall;

  @Override
  public String createProduct(ProductDto product) {
    Product productClass = new Product();
    BeanUtils.copyProperties(product, productClass);
    productRepository.save(productClass);
    /**
     * Implement rollback from mongodb incase solr is not working
     */
    ProductSearchDto productSearchDto = new ProductSearchDto(product);
    //productsApiCall.addNewProduct(productSearchDto);
    return "Success";
  }

  @Override
  public double getMerchantRating(String productId) {
    List <Product> product = productRepository.findByProductId(productId);
    return product.get(0).getMerchantRating();
  }

  @Override
  public List <Product> getProductList() {
    /**
     * Directly return if possible
     * no need to create extra variables
     */
    return productRepository.findAll();

  }

  @Override
  public List <Product> getProductById(String productId) {
    List <Product> productById = productRepository.findByProductId(productId);
    return productById;
  }

  @Override
  public List <Product> getMerchantById(String productId) {
    List <Product> merchantByCategory = productRepository.findByProductId(productId);
    return merchantByCategory;
  }

  @Override
  public List <Product> getProductsByCategory(String productCategory) {
    List <Product> productByCategory = productRepository.findByProductCategory(productCategory);
    return productByCategory;
  }

  @Override
  public List <Product> getProductsSortByPrice() {
    List <Product> productSortedByPrice = productRepository.findAll(new Sort(Sort.Direction.ASC, PRODUCT_PRICE));
    return productSortedByPrice;
  }

  @Override
  public List <Product> getProductSortByRating() {
    List <Product> productSortedByRating = productRepository.findAll(new Sort(Sort.Direction.DESC, PRODUCT_RATING));
    return productSortedByRating;
  }

  @Override
  public boolean reduceProductCount(String productId, int quantity) {
    int productQuantity = -quantity;
    UpdateResult result = mongoOperations.updateFirst(new Query(Criteria.where("_id").is(productId)),
        new Update().inc("unitStock", productQuantity), Product.class);
    /**
     * You can directly return instead of using if
     */
    if (result.isModifiedCountAvailable()) {
      return true;
    }
    return false;
  }

  @Override
  public List <Product> getMerchantByName(String productName) {
    List <Product> productsMerchantName = productRepository.findByProductName(productName);
    return productsMerchantName;
  }


}
