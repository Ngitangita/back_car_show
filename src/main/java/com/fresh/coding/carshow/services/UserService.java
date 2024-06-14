package com.fresh.coding.carshow.services;

import com.fresh.coding.carshow.dtos.requests.UserRequest;
import com.fresh.coding.carshow.dtos.responses.Paginate;
import com.fresh.coding.carshow.dtos.responses.UserSummarized;

import java.util.List;

public interface UserService {
    Paginate<List<UserSummarized>> findAllUsers(Integer page, Integer perPage);

    UserSummarized updateUser(Long id, UserRequest userRequest);

    UserSummarized findUserById(Long id);

    UserSummarized deleteUserById(Long id);
}
