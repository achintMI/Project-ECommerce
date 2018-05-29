package com.order.Order.Service;

import com.order.Order.DTO.OrderDto;
import com.order.Order.Model.OrderModel;

import java.util.List;

public interface OrderInterface {
    void addProductToCart(OrderDto orderDto);

    List<OrderModel> getCartHistory(String userId);

}
