package com.example.order.dto;
import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private String username;
    private List<OrderItemRequest> items;
}