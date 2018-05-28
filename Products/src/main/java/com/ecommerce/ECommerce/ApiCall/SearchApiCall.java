package com.ecommerce.ECommerce.ApiCall;

import com.ecommerce.ECommerce.DTO.ProductSearchDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component("searchapi")
public class SearchApiCall {

    @Value("${search.client}")
    private String SEARCH_API;

    public void addNewProduct(ProductSearchDto productSearchDto){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SEARCH_API+"/addnewproduct")
                .queryParam("pid", productSearchDto.getPid())
                .queryParam("productName", productSearchDto.getProductName())
                .queryParam("productPrice", productSearchDto.getProductPrice())
                .queryParam("stock", productSearchDto.getStock())
                .queryParam("productDescription", productSearchDto.getProductDescription())
                .queryParam("merchantName", productSearchDto.getMerchantName())
                .queryParam("productCategory", productSearchDto.getProductCategory())
                .queryParam("productRating", productSearchDto.getProductRating())
                .queryParam("isIndexed", productSearchDto.isIndexed())
                .queryParam("mid", productSearchDto.getMid())
                .queryParam("imgSrc", productSearchDto.getImgSrc());
        HttpEntity<?> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Boolean> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                Boolean.class);
    }
}
