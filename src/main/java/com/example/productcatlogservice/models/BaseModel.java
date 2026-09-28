package com.example.productcatlogservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {

  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)   // It will create a new category id every time a new product is added.
  private long id;

  @Column(nullable = false, updatable = false)
  @CreatedDate
  private Date createdAt;

  @Column(nullable = false)
  @LastModifiedDate
  private Date updatedAt;

  @Enumerated(value = EnumType.STRING)
  private Status status;

  public BaseModel() {
    this.status = Status.ACTIVE;
  }
}
