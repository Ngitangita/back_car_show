package com.fresh.coding.carshow.controllers;

import com.fresh.coding.carshow.dtos.requests.UserRequest;
import com.fresh.coding.carshow.dtos.responses.Paginate;
import com.fresh.coding.carshow.dtos.responses.UserSummarized;
import com.fresh.coding.carshow.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping
    public Paginate<List<UserSummarized>> getAllUsers(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer perPage
    ) {
        return userService.findAllUsers(page, perPage);
    }

    @GetMapping("/{id}")
    public UserSummarized getUser(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PutMapping("/{id}")
    public UserSummarized updateUser(@PathVariable Long id, @RequestBody @Valid UserRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }

    @DeleteMapping("/{id}")
    public UserSummarized deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
}
