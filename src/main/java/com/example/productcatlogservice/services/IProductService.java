package com.example.productcatlogservice.services;

import com.example.productcatlogservice.exceptions.ProductAlreadyExistsException;
import com.example.productcatlogservice.exceptions.ProductNotFoundException;
import com.example.productcatlogservice.models.Product;

import java.util.List;

public interface IProductService {
  Product getProductById(long id) throws ProductNotFoundException;
  Product addProduct(Product product) throws ProductAlreadyExistsException;
  Product replaceProduct(long id, Product product) throws ProductNotFoundException;
  Boolean deleteProduct(long id) throws ProductNotFoundException;
  List<Product> getAllProducts();

}
