package com.example.order.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data @Builder @AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String username;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private List<ItemDetail> items;

    @Data @Builder @AllArgsConstructor
    public static class ItemDetail {
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal price;
    }
}