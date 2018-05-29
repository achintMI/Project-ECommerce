package com.order.Order.Service;

import com.order.Order.ApiCall.ProductsApiCall;
import com.order.Order.DTO.OrderDto;
import com.order.Order.Email.EmailHtmlSender;
import com.order.Order.Email.EmailStatus;
import com.order.Order.Model.OrderModel;
import com.order.Order.Repository.OrderRepositoryInterface;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import java.util.List;


@Service
public class OrderService implements  OrderInterface{

    @Autowired
    private OrderRepositoryInterface orderRepositoryInterface;

    @Autowired
    @Qualifier("productapi")
    public ProductsApiCall productsApiCall;

    @Autowired
    public EmailHtmlSender emailHtmlSender;

    @Override
    public void addProductToCart(OrderDto orderDto) {
        OrderModel order = new OrderModel();
        BeanUtils.copyProperties(orderDto, order);
        orderRepositoryInterface.save(order);
        productsApiCall.changeProductCount(order.getProductId(), order.getQuantity());
        Context context = new Context();
        context.setVariable("productId", orderDto.getProductId());
        context.setVariable("productCost", orderDto.getTotalCost());
        context.setVariable("productUrl", orderDto.getProductUrl());
        EmailStatus emailStatus = emailHtmlSender.send("achintachu@gmail.com", "Congrats Your order has been placed", "mailTemplate", context);
    }


    @Override
    public List<OrderModel> getCartHistory(String userId) {
        List<OrderModel> order = orderRepositoryInterface.findByUserId(userId);
        return order;
    }
}
