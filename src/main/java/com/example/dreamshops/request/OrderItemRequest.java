package com.example.dreamshops.request;

import com.example.dreamshops.model.OrderItem;
import com.example.dreamshops.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {
    private int quantity;

    private Long productId;

    public static OrderItem toEntity(OrderItemRequest request, Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(request.getQuantity());
        orderItem.setUnitPrice(product.getPrice());
        orderItem.setNewTotalPrice();

        return orderItem;
    }
}
