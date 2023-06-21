package com.vmwebshop.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Integer id;
    private String title;

    private String description;

    private Double price;

    private Integer stock;

    private String condition;

    private Integer userId;

    private String image;
}

