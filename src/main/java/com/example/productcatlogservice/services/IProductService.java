package com.example.productcatlogservice.services;

import com.example.productcatlogservice.exceptions.ProductAlreadyExistsException;
import com.example.productcatlogservice.exceptions.ProductNotFoundException;
import com.example.productcatlogservice.exceptions.UnauthorizedAccessException;
import com.example.productcatlogservice.exceptions.UserNotFoundException;
import com.example.productcatlogservice.models.Product;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IProductService {
  Product getProductById(long id) throws ProductNotFoundException;
  Product addProduct(Product product) throws ProductAlreadyExistsException;
  Product replaceProduct(long id, Product product) throws ProductNotFoundException;
  Boolean deleteProduct(long id) throws ProductNotFoundException;
  List<Product> getAllProducts();
  Product getProductDetailsBasedOnUserRole(@PathVariable long productId, @PathVariable long userId) throws ProductNotFoundException, UserNotFoundException, UnauthorizedAccessException;

}
