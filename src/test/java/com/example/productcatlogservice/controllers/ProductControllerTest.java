package com.example.productcatlogservice.controllers;

import com.example.productcatlogservice.dtos.ProductDto;
import com.example.productcatlogservice.exceptions.ProductNotFoundException;
import com.example.productcatlogservice.models.Product;
import com.example.productcatlogservice.services.IProductService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

  @Autowired
  private ProductController productController;

  @MockBean
  private IProductService productService;

  @SneakyThrows
  @Test
  public void TestGetProductById_WithValidId_ReturnProductSuccessfully() {
    //    Arrange
    Long productId = 1L;

    Product product = new Product();
    product.setId(productId);

    when(productService.getProductById(productId)).thenReturn(product);

    //    Act
    ResponseEntity<ProductDto> responseEntity = productController.getProductById(productId);

    //    Assert
    assertNotNull(responseEntity);
    assertNotNull(responseEntity.getBody());
    assertNotNull(responseEntity.getBody().getId());
    assertEquals(productId, responseEntity.getBody().getId());
  }

  @Test
  public void TestGetProductById_WithNegativeId_ReturnsIllegalArgumentException() {
    Long productId = -1L;

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                                                      () -> productController.getProductById(productId));

    assertEquals("Please enter a valid product Id", exception.getMessage());
  }

  @Test
  public void TestGetProductById_WithIdAsZero_ReturnsIllegalArgumentException() {
    Long productId = 0L;

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productController.getProductById(productId));

    assertEquals("Product Id should be greater than 0", exception.getMessage());
  }

  @SneakyThrows
  @Test
  public void TestGetProductById_WithIdNotInDb_ReturnsProductNotFoundException() {
    Long productId = 1L;

    when(productService.getProductById(productId)).thenThrow(new ProductNotFoundException("Product Not Found"));

    assertThrows(ProductNotFoundException.class, () -> productController.getProductById(productId));
  }

  @SneakyThrows
  @Test
  public void TestGetProductById_WhereProductServiceReturnsNullProduct_ReturnsRuntimeException() {
    Long productId = 99L;

    when(productService.getProductById(productId)).thenReturn(null);

    assertThrows(RuntimeException.class, () -> productController.getProductById(productId));
  }

}