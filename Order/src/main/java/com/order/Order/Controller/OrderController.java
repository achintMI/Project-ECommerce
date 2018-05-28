package com.order.Order.Controller;

import com.google.common.collect.Lists;
import com.order.Order.ApiCall.ProductsApiCall;
import com.order.Order.DTO.OrderDto;
import com.order.Order.MailSender;
import com.order.Order.Model.OrderModel;
import com.order.Order.Service.OrderInterface;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.criteria.Order;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderInterface orderInterface;

    @Autowired
    @Qualifier("mailsend")
    public MailSender mailSender;

    @Autowired
    @Qualifier("productapi")
    public ProductsApiCall productsApiCall;

    @RequestMapping("place-order")
    public OrderDto placeOrder(@RequestParam String emailUser, String productUrl, String productId, String userId, String merchantId, double cost) throws AddressException {
        OrderDto orderDto = new OrderDto(productUrl, productId, userId, merchantId, cost);
        orderInterface.addProductToCart(orderDto);
        mailSender.setMail(emailUser);
        productsApiCall.changeProductCount(productId);
        return orderDto;
    }

    @RequestMapping("order-history")
    public List<OrderModel> getCartHistory(@RequestParam String userId){
        return orderInterface.getCartHistory(userId);
    }

}
