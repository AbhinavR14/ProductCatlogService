package com.example.productcatlogservice.TableInheritenceExamples.TablePerClass;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity(name = "tpc_instructor")
public class Instructor extends User {
  private String company;
}
