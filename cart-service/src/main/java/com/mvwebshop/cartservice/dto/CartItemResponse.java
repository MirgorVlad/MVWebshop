package com.mvwebshop.cartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private Long id;
    //private Long productId;
    private Integer quantity;
    private Long userId;
    private ProductResponse productResponse;

}
