package org.accountservice;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "UserService")
public interface UserServiceClient {


    @GetMapping("/user/{id}")
    UserDto getUserById(@PathVariable("id") Integer userId);




}
