package com.example.productcatlogservice.controllers;

import com.example.productcatlogservice.dtos.CategoryDto;
import com.example.productcatlogservice.dtos.ProductDto;
import com.example.productcatlogservice.exceptions.ProductAlreadyExistsException;
import com.example.productcatlogservice.exceptions.ProductNotFoundException;
import com.example.productcatlogservice.models.Category;
import com.example.productcatlogservice.models.Product;
import com.example.productcatlogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired
  private IProductService productService;

  @GetMapping("/{id}")
  public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) throws ProductNotFoundException {
    if (id <= 0)
        throw new IllegalArgumentException("Please enter a valid product ID");

    Product product = productService.getProductById(id);
    if (product == null)
        throw new RuntimeException("Product not found");
//  All the exception thrown in this class will go to the ControllerAdvisor class annotated with @RestControllerAdvice.

    ProductDto productDto = getProductDtoFromProduct(product);
    return new ResponseEntity<>(productDto, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) throws ProductAlreadyExistsException {
    Product product = productService.addProduct(getProductFromProductDto(productDto));
    return new ResponseEntity<>(getProductDtoFromProduct(product), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductDto> replaceProduct(@PathVariable Long id,
                                                   @RequestBody ProductDto requestProductDto) throws ProductNotFoundException {

    if (id <= 0)
        throw new IllegalArgumentException("Please enter a valid product ID");

    Product inputProduct = getProductFromProductDto(requestProductDto);
    Product replacedProduct = productService.replaceProduct(id, inputProduct);

    if (replacedProduct == null)
      throw new ProductNotFoundException("Product with id " + id + " does not exist!");

    ProductDto productDto = getProductDtoFromProduct(replacedProduct);
    return new ResponseEntity<>(productDto, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<ProductDto>> getAllProducts() {
    List<Product> products = productService.getAllProducts();
    List<ProductDto> productDtos = new ArrayList<>();

    for (Product product : products)
      productDtos.add(getProductDtoFromProduct(product));

    return new ResponseEntity<>(productDtos, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> deleteProductById(@PathVariable Long id) throws ProductNotFoundException {
    Boolean hasDeleted = productService.deleteProduct(id);
    return new ResponseEntity<>(hasDeleted, HttpStatus.OK);
  }

  private ProductDto getProductDtoFromProduct(Product product) {
    ProductDto productDto = new ProductDto();
    productDto.setName(product.getName());
    productDto.setDescription(product.getDescription());
    productDto.setId(product.getId());
    productDto.setQuantity(product.getQuantity());
    productDto.setPrice(product.getPrice());
    productDto.setImageUrl(product.getImageUrl());

    CategoryDto categoryDto = new CategoryDto();
    categoryDto.setId(product.getCategory().getId());
    categoryDto.setName(product.getCategory().getName());
    categoryDto.setDescription(product.getCategory().getDescription());
    productDto.setCategory(categoryDto);

    return productDto;
  }

  private Product getProductFromProductDto(ProductDto productDto) {
    Product product = new Product();
    product.setId(productDto.getId());
    product.setName(productDto.getName());
    product.setDescription(productDto.getDescription());
    product.setImageUrl(productDto.getImageUrl());
    product.setPrice(productDto.getPrice());
    product.setQuantity(productDto.getQuantity());

    Category category = new Category();
    category.setId(productDto.getCategory().getId());
    category.setName(productDto.getCategory().getName());
    category.setDescription(productDto.getCategory().getDescription());
    product.setCategory(category);

    return product;
  }

}
