package com.example.productcatlogservice.services;

import com.example.productcatlogservice.dtos.SortParam;
import com.example.productcatlogservice.dtos.SortType;
import com.example.productcatlogservice.models.Product;
import com.example.productcatlogservice.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService implements ISearchService {
  @Autowired
  private ProductRepo productRepo;

  @Override
  public Page<Product> searchProducts(String query, Integer pageSize, Integer pageNumber, List<SortParam> sortParams) {
//    Sort sortByPrice = Sort.by("price");
//    Sort sortById = Sort.by("id").descending();
//    Sort sort = sortByPrice.and(sortById);

    Sort sort = createSort(sortParams);

    return productRepo.findByNameContaining(query, PageRequest.of(pageNumber, pageSize, sort));
  }

  private Sort createSort(List<SortParam> sortParams) {
    if (sortParams == null || sortParams.isEmpty())
      return Sort.unsorted();

//    List<Sort.Order> orders = new ArrayList<>();
//    for (SortParam sortParam : sortParams) {
//      if (sortParam.getSortType() == SortType.ASC)
//        orders.add(Sort.Order.asc(sortParam.getParamName()));
//      else
//        orders.add(new Sort.Order(Sort.Direction.DESC, sortParam.getParamName()));
//    }

    List<Sort.Order> orders = sortParams.stream()
            .map(param -> param.getSortType() == SortType.ASC ? Sort.Order.asc(param.getParamName()) :
                    Sort.Order.desc(param.getParamName()))
            .toList();

    return Sort.by(orders);
  }
}
