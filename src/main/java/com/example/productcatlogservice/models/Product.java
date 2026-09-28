package com.example.productcatlogservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
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
  private boolean isPrimeSaleSpecific;
  private boolean isListed;

  @ManyToOne(cascade = CascadeType.ALL)
  @JsonManagedReference
  private Category category;

}
