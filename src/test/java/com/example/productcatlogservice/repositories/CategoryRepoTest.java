package com.example.productcatlogservice.repositories;

import com.example.productcatlogservice.models.Category;
import com.example.productcatlogservice.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepoTest {

  @Autowired
  private CategoryRepo categoryRepo;

  @Autowired
  private ProductRepo productRepo;

  @Test
  @Transactional
  public void testFetchTypesAndModes() {
    Category category = categoryRepo.findById(1L).get();
    System.out.println(category.getName());

    for (Product product : category.getProducts())
      System.out.println(product.getName());
  }

  @Test
  @Transactional
  public void testNPlusOneProblem() {
    List<Category> categories = categoryRepo.findAll();
    for (Category category :  categories) {
      System.out.println(category.getName());

      for (Product product : category.getProducts())
        System.out.println(product.getName());
    }
  }

}