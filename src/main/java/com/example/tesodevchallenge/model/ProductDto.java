package com.example.tesodevchallenge.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class ProductDto {


    private UUID id;
    private String productType;
    private Integer price;
    private Integer stock;
}
