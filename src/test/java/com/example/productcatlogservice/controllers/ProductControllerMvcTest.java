package com.example.productcatlogservice.controllers;

import com.example.productcatlogservice.dtos.ProductDto;
import com.example.productcatlogservice.models.Product;
import com.example.productcatlogservice.services.IProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerMvcTest {

  @MockBean
  private IProductService productService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void GetAllProducts_RunsSuccessfully() throws Exception {
    // // //    For Mocking     // // //
    Product product1 = new Product();
    product1.setId(1L);
    product1.setName("Product 1");

    Product product2 = new Product();
    product2.setId(2L);
    product2.setName("Product 2");

    List<Product> products = new ArrayList<>();
    products.add(product1);
    products.add(product2);

    when(productService.getAllProducts()).thenReturn(products);

    // // //    For Asserting/Expecting   // // //
    ProductDto productDto1 = new ProductDto();
    productDto1.setId(product1.getId());
    productDto1.setName("Product 1");

    ProductDto productDto2 = new ProductDto();
    productDto2.setId(product2.getId());
    productDto2.setName("Product 2");

    List<ProductDto> productDtos = new ArrayList<>();
    productDtos.add(productDto1);
    productDtos.add(productDto2);

    // //    Object <---> JSON <---> String (Stringified JSON)
    String expectedResponse = objectMapper.writeValueAsString(productDtos);

    mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));
  }
}
