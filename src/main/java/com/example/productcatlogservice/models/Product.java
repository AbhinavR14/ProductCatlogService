package com.example.productcatlogservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product extends BaseModel {
  private String name;
  private double price;
  private String description;
  private long quantity;
  private String imageUrl;
  private Category category;
  private boolean isPrimeSaleSpecific;
}
