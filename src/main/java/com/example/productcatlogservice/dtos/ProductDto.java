package com.example.productcatlogservice.dtos;

import com.example.productcatlogservice.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
  private long id;
  private String name;
  private double price;
  private String description;
  private long quantity;
  private String imageUrl;
  private CategoryDto category;
}
