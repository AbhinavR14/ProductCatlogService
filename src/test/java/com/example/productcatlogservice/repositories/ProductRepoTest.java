package com.example.productcatlogservice.repositories;

import com.example.productcatlogservice.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepoTest {

  @Autowired
  private ProductRepo productRepo;

  @Test
  @Transactional
  public void testQueries() {
    List<Product> products = productRepo.findProductByPriceBetween(1D, 500D);
    System.out.println(products.size() == 1);
  }
}