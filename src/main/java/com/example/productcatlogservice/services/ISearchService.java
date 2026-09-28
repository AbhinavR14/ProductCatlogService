package com.example.productcatlogservice.services;

import com.example.productcatlogservice.dtos.SortParam;
import com.example.productcatlogservice.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISearchService {
  public Page<Product> searchProducts(String query, Integer pageSize, Integer pageNumber, List<SortParam> sortParams);
}
