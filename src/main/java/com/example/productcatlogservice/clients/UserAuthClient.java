package com.example.productcatlogservice.clients;

import com.example.productcatlogservice.dtos.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userauthservice")
public interface UserAuthClient {

  @GetMapping("/users/{userId}")
  UserDto getUserById(@PathVariable("userId") Long userId);
}
