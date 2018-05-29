package com.order.Order.Controller;

import com.order.Order.DTO.OrderDto;
import com.order.Order.Model.OrderModel;
import com.order.Order.Service.OrderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.AddressException;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderInterface orderInterface;

    @RequestMapping("place-order")
    public OrderDto placeOrder(@RequestParam String emailUser, String productUrl, String productId, String userId, String merchantId, double cost, int quantity) throws AddressException {
        OrderDto orderDto = new OrderDto(productUrl, productId, userId, merchantId, cost, quantity);
        orderInterface.addProductToCart(orderDto);
        return orderDto;
    }

    @RequestMapping("order-history")
    public List<OrderModel> getCartHistory(@RequestParam String userId){
        return orderInterface.getCartHistory(userId);
    }

}
