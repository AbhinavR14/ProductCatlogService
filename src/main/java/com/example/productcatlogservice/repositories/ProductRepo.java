package com.example.productcatlogservice.repositories;

import com.example.productcatlogservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
  List<Product> findAllByOrderByNameAsc();
  List<Product> findProductByPriceBetween(Double priceStart, Double priceEnd);

  @Query("SELECT c.name from Product p join Category c on p.category.id=c.id where p.id = :id")
  String getCategoryNameCorrespondingToProductId(Long id);
}
