package com.example.productcatlogservice.controllers;

import com.example.productcatlogservice.dtos.CategoryDto;
import com.example.productcatlogservice.dtos.ProductDto;
import com.example.productcatlogservice.dtos.SearchRequestDto;
import com.example.productcatlogservice.models.Product;
import com.example.productcatlogservice.services.ISearchService;
import com.example.productcatlogservice.utilis.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
  @Autowired
  private ISearchService searchService;

  @PostMapping
  public ResponseEntity<Page<ProductDto>> searchProducts(@RequestBody SearchRequestDto searchRequestDto) {
    Page<Product> productPage = searchService.searchProducts(
                                                searchRequestDto.getQuery(),
                                                searchRequestDto.getPageSize(),
                                                searchRequestDto.getPageNumber(),
                                                searchRequestDto.getSortParams());

//    List<ProductDto> productDtos = productPage.get().map(ProductMapper::getProductDtoFrom).toList();
//    Page<ProductDto> productDtoPage = new PageImpl(productDtos);

    Page<ProductDto> productDtoPage = ProductMapper.getProductDtoPage(productPage);

    return ResponseEntity.ok(productDtoPage);
  }
}
