package com.example.productcatlogservice.services;

import com.example.productcatlogservice.dtos.RoleDto;
import com.example.productcatlogservice.dtos.UserDto;
import com.example.productcatlogservice.exceptions.ProductAlreadyExistsException;
import com.example.productcatlogservice.exceptions.ProductNotFoundException;
import com.example.productcatlogservice.exceptions.UnauthorizedAccessException;
import com.example.productcatlogservice.exceptions.UserNotFoundException;
import com.example.productcatlogservice.models.Product;
import com.example.productcatlogservice.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service("storageProductService")
@Primary
public class StorageProductService implements IProductService {

  @Autowired
  private ProductRepo productRepo;

  @Autowired
  private RestTemplate restTemplate;

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

  @Override
  public Product getProductDetailsBasedOnUserRole(long productId, long userId)
          throws ProductNotFoundException, UserNotFoundException, UnauthorizedAccessException {

    Product product = productRepo.findById(productId)
                                  .orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " does not exist!"));

//    UserDto userDto = restTemplate.getForEntity("http://userauthservice/users/{userId}", UserDto.class, userId).getBody();
    ResponseEntity<UserDto> userDtoResponse = restTemplate.getForEntity("http://userauthservice/users/{userId}",
                                                                        UserDto.class, userId);

    if (!userDtoResponse.hasBody() || userDtoResponse.getStatusCode() != HttpStatus.OK)
        throw new UserNotFoundException("User with id " + userId + " does not exist!");

    UserDto userDto = userDtoResponse.getBody();

    // 1. Check for product visibility. If listed then directly return product details.
    if (product.isListed())
      return product;

    // 2. If not listed, then, return product details if user is ADMIN.
    for (RoleDto roleDto : userDto.getRoles()) {
      if (roleDto.getRoleName().equals("ADMIN"))
        return product;
    }

    throw new UnauthorizedAccessException("Unauthorized access!");
  }
}
