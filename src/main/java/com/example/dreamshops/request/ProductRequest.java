package com.example.dreamshops.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int inventory;
    private String categoryName;
}
