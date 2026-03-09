package com.example.productcatlogservice.TableInheritenceExamples.JoinedClass;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name = "jc_ta")
@PrimaryKeyJoinColumn(name = "user_id")   // This can be ANY name we want for foreign key column in the "Ta" class.
public class Ta extends User {
  private Long hours;
}
