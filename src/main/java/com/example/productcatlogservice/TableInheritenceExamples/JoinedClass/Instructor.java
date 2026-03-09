package com.example.productcatlogservice.TableInheritenceExamples.JoinedClass;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name = "jc_instructor")
@PrimaryKeyJoinColumn(name = "jc_user_id_any_name")
public class Instructor extends User {
  private String company;
}
