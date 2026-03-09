package com.example.productcatlogservice.TableInheritenceExamples.TablePerClass;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity(name = "tpc_ta")
public class Ta extends User {
  private Long hours;
}
