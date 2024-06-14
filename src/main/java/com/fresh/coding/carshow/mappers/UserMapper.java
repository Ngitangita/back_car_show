package com.fresh.coding.carshow.mappers;

import com.fresh.coding.carshow.dtos.requests.UserRequest;
import com.fresh.coding.carshow.dtos.responses.UserSummarized;
import com.fresh.coding.carshow.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest userRequest) {
        return (new User())
                .setEmail(userRequest.email())
                .setPwd(userRequest.password())
                .setName(userRequest.name())
                .setId(userRequest.id());
    }


    public UserSummarized toResponse(User user) {
        return new UserSummarized(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPassword()
        );
    }

}
