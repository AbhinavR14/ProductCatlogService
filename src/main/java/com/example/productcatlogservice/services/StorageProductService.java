package com.example.productcatlogservice.services;

import com.example.productcatlogservice.exceptions.ProductAlreadyExistsException;
import com.example.productcatlogservice.exceptions.ProductNotFoundException;
import com.example.productcatlogservice.models.Product;
import com.example.productcatlogservice.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class StorageProductService implements IProductService {

  @Autowired
  private ProductRepo productRepo;

  @Override
  public Product getProductById(long id) throws ProductNotFoundException {
    Optional<Product> productOptional = productRepo.findById(id);
    if (productOptional.isEmpty())
      throw new ProductNotFoundException("Product with id " + id + " does not exist!");

    return productOptional.get();
  }

  @Override
  public Product addProduct(Product product) throws ProductAlreadyExistsException {
    Optional<Product> productOptional = productRepo.findById(product.getId());
    if (productOptional.isPresent())
      throw new ProductAlreadyExistsException("Product with id " + product.getId() + " already not exist!");

    return productRepo.save(product);
  }

  @Override
  public Product replaceProduct(long id, Product product) throws ProductNotFoundException {
    Optional<Product> productOptional = productRepo.findById(id);
    if (productOptional.isEmpty())
      throw new ProductNotFoundException("Product with id " + id + " does not exist!");

    return productRepo.save(product);
  }

  @Override
  public Boolean deleteProduct(long id) throws ProductNotFoundException {
    Optional<Product> productOptional = productRepo.findById(id);
    if (productOptional.isEmpty())
      throw new ProductNotFoundException("Product with id " + id + " does not exist!");

    productRepo.deleteById(id);
    return true;
  }

  @Override
  public List<Product> getAllProducts() {
    return productRepo.findAll();
  }
}
