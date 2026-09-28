package com.example.productcatlogservice.services;

import com.example.productcatlogservice.dtos.FakeStoreProductDto;
import com.example.productcatlogservice.models.Category;
import com.example.productcatlogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements IProductService{
  @Autowired
  private RestTemplateBuilder restTemplateBuilder;

  @Override
  public Product getProductById(long id) {
    RestTemplate restTemplate = restTemplateBuilder.build();
//    FakeStoreDto fakeStoreDto = restTemplate.getForObject("https://fakestoreapi.com/products/{id}",
//                                                  FakeStoreDto.class, id);

    ResponseEntity<FakeStoreProductDto> fakeStoreDtoResponseEntity = restTemplate.
                  getForEntity("http://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, id);

    if (fakeStoreDtoResponseEntity.hasBody()
            && fakeStoreDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)))
      return getProductFromDto(fakeStoreDtoResponseEntity.getBody());

    return null;
  }

  @Override
  public Product addProduct(Product product) {
    String url = "https://fakestoreapi.com/products";
    FakeStoreProductDto fakeStoreProductDto = getDtoFromProduct(product);

    ResponseEntity<FakeStoreProductDto> responseEntity = requestForEntity(HttpMethod.POST,
                                                                                      url,
                                                                                      fakeStoreProductDto,
                                                                                      FakeStoreProductDto.class);

    if (responseEntity.hasBody() && responseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)))
      return getProductFromDto(responseEntity.getBody());

    return null;
  }

  @Override
  public Product replaceProduct(long id, Product product) {
    FakeStoreProductDto fakeStoreProductDto = getDtoFromProduct(product);
    ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntity(
            HttpMethod.PUT, "https://fakestoreapi.com/products/{id}", fakeStoreProductDto, FakeStoreProductDto.class, id);

    if (fakeStoreProductDtoResponseEntity.hasBody()
            && fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)))
      return getProductFromDto(fakeStoreProductDtoResponseEntity.getBody());

    return null;
  }

  @Override
  public Boolean deleteProduct(long id) {
    String url = "https://fakestoreapi.com/products/{id}";
    ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntity(HttpMethod.DELETE,
                                                                                      url,
                                                                                      null,
                                                                                      FakeStoreProductDto.class, id);

    return fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) ? true : false;
  }

  @Override
  public List<Product> getAllProducts() {
    String url = "https://fakestoreapi.com/products";
    ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDtosResponseEntity = requestForEntity(HttpMethod.GET,
                                                                                    url,
                                                                                    null,
                                                                                    FakeStoreProductDto[].class);

    if (!fakeStoreProductDtosResponseEntity.hasBody() &&
      fakeStoreProductDtosResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(400)))
        return new ArrayList<>();

    FakeStoreProductDto[] fakeStoreProductDtos = fakeStoreProductDtosResponseEntity.getBody();
    List<Product> products = Arrays.stream(fakeStoreProductDtos)
            .map(this::getProductFromDto)
            .toList();

    return products;
  }

  @Override
  public Product getProductDetailsBasedOnUserRole(long productId, long userId) {
    return null;
  }

  public <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
    RestTemplate restTemplate = restTemplateBuilder.build();
    RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
    ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
    return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
  }

  private Product getProductFromDto(FakeStoreProductDto fakeStoreProductDto) {
    Product product = new Product();
    product.setId(fakeStoreProductDto.getId());
    product.setName(fakeStoreProductDto.getTitle());
    product.setDescription(fakeStoreProductDto.getDescription());
    product.setPrice(fakeStoreProductDto.getPrice());
    product.setImageUrl(fakeStoreProductDto.getImage());

    Category category = new Category();
    category.setName(fakeStoreProductDto.getCategory());
    product.setCategory(category);

    return product;
  }

  private FakeStoreProductDto getDtoFromProduct(Product product) {
    FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
    fakeStoreProductDto.setId(product.getId());
    fakeStoreProductDto.setTitle(product.getName());
    fakeStoreProductDto.setDescription(product.getDescription());
    fakeStoreProductDto.setPrice(product.getPrice());
    fakeStoreProductDto.setImage(product.getImageUrl());
    fakeStoreProductDto.setCategory(product.getCategory().getName());

    return fakeStoreProductDto;
  }
}
