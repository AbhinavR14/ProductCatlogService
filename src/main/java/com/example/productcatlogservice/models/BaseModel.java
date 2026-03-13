package com.example.productcatlogservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {

  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)   // It will create a new category id every time a new product is added.
  private long id;

  private Date createdAt;
  private Date lastUpdatedAt;

  @Enumerated(value = EnumType.STRING)
  private Status status;

  public BaseModel() {
    this.createdAt = new Date();
    this.lastUpdatedAt = new Date();
    this.status = Status.ACTIVE;
  }
}
