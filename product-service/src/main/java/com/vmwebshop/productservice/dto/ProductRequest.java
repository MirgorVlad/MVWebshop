package com.vmwebshop.productservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String title;

    private String description;

    private Double price;

    private Integer stock;

    private String condition;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("img")
    private String image;
}
