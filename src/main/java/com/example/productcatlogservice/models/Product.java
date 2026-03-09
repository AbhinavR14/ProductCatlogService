package com.example.productcatlogservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel {
  private String name;
  private double price;
  private String description;
  private long quantity;
  private String imageUrl;

  @ManyToOne
  private Category category;
  private boolean isPrimeSaleSpecific;
}
