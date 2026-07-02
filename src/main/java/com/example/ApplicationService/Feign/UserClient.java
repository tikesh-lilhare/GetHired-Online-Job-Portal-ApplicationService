package com.example.ApplicationService.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USERSERVICE")
public interface UserClient {
    @GetMapping("/users/profile/{id}")
    UserDTO getUser(
            @PathVariable("id")
            Long id
    );
}
