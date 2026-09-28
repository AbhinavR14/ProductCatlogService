package com.example.productcatlogservice.utilis;

import com.example.productcatlogservice.dtos.CategoryDto;
import com.example.productcatlogservice.dtos.ProductDto;
import com.example.productcatlogservice.models.Category;
import com.example.productcatlogservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class ProductMapper {
  public static ProductDto getProductDtoFrom(Product product) {
    ProductDto productDto = new ProductDto();
    productDto.setName(product.getName());
    productDto.setDescription(product.getDescription());
    productDto.setId(product.getId());
    productDto.setQuantity(product.getQuantity());
    productDto.setPrice(product.getPrice());
    productDto.setImageUrl(product.getImageUrl());
    productDto.setListed(product.isListed());

    if (product.getCategory() != null) {
      CategoryDto categoryDto = new CategoryDto();
      categoryDto.setId(product.getCategory().getId());
      categoryDto.setName(product.getCategory().getName());
      categoryDto.setDescription(product.getCategory().getDescription());
      productDto.setCategory(categoryDto);
    }

    return productDto;
  }

  public static Product getProductFrom(ProductDto productDto) {
    Product product = new Product();
    product.setId(productDto.getId());
    product.setName(productDto.getName());
    product.setDescription(productDto.getDescription());
    product.setImageUrl(productDto.getImageUrl());
    product.setPrice(productDto.getPrice());
    product.setQuantity(productDto.getQuantity());
    product.setListed(productDto.isListed());

    if (productDto.getCategory() != null) {
      Category category = new Category();
      category.setId(productDto.getCategory().getId());
      category.setName(productDto.getCategory().getName());
      category.setDescription(productDto.getCategory().getDescription());
      product.setCategory(category);
    }

    return product;
  }

  public static Page<ProductDto> getProductDtoPage(Page<Product> productPage) {
    List<ProductDto> productDtos = productPage.get().map(ProductMapper::getProductDtoFrom).toList();
    return new PageImpl<>(productDtos, productPage.getPageable(), productPage.getTotalElements());
  }
}
