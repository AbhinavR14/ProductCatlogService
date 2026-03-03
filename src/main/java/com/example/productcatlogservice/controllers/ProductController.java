package com.example.productcatlogservice.controllers;

import com.example.productcatlogservice.dtos.CategoryDto;
import com.example.productcatlogservice.dtos.ProductDto;
import com.example.productcatlogservice.models.Category;
import com.example.productcatlogservice.models.Product;
import com.example.productcatlogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired
  private IProductService productService;

  @GetMapping("/{id}")
  public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
    if (id <= 0)
        throw new IllegalArgumentException("Please enter a valid product ID");

    Product product = productService.getProductById(id);
    if (product == null)
        throw new RuntimeException("Product not found");
//  All the exception thrown in this class will go to the ControllerAdvisor class annotated with @RestControllerAdvice.

    ProductDto productDto = getProductDtoFromProduct(product);
    return new ResponseEntity<>(productDto, HttpStatus.OK);
  }

  @PostMapping()
  public Product createProduct(@RequestBody Product product) {
    product.setImageUrl("abc.com");
    return product;
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductDto> replaceProduct(@PathVariable Long id, @RequestBody ProductDto requestProductDto) {
    if (id <= 0)
        throw new IllegalArgumentException("Please enter a valid product ID");

    Product inputProduct = getProductFromProductDto(requestProductDto);
    Product replacedProduct = productService.replaceProductById(id, inputProduct);

    if (replacedProduct == null)
      throw new RuntimeException("Product not found");

    ProductDto productDto = getProductDtoFromProduct(replacedProduct);
    return new ResponseEntity<>(productDto, HttpStatus.OK);
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
    categoryDto.setName(product.getCategory().getName());
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
    product.setCategory(product.getCategory());
    return product;
  }

}
