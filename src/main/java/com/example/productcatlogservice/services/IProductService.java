package com.example.productcatlogservice.services;

import com.example.productcatlogservice.models.Product;
import org.springframework.http.ResponseEntity;

public interface IProductService {
  Product getProductById(long id);
  Product replaceProductById(long id, Product product);
}
