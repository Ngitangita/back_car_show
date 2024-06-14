package com.fresh.coding.carshow.services.impl;

import com.fresh.coding.carshow.dtos.requests.UserRequest;
import com.fresh.coding.carshow.dtos.responses.Paginate;
import com.fresh.coding.carshow.dtos.responses.UserSummarized;
import com.fresh.coding.carshow.entities.User;
import com.fresh.coding.carshow.exceptions.NotFoundException;
import com.fresh.coding.carshow.mappers.UserMapper;
import com.fresh.coding.carshow.repositories.UserRepository;
import com.fresh.coding.carshow.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Paginate<List<UserSummarized>> findAllUsers(Integer page, Integer perPage) {
        var pageRequest = PageRequest.of(page - 1, perPage);
        return getListPaginate(userRepository.findAll(pageRequest));
    }

    @Override
    public UserSummarized updateUser(Long id, UserRequest userRequest) {
        var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        user.setPwd(userRequest.password() != null ? passwordEncoder.encode(userRequest.password()) : null);
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        var savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserSummarized findUserById(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return userMapper.toResponse(user);
    }

    @Override
    public UserSummarized deleteUserById(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.deleteById(id);
        return userMapper.toResponse(user);
    }

    private Paginate<List<UserSummarized>> getListPaginate(Page<User> users) {
        var items = users.getContent().stream().map(userMapper::toResponse).toList();
        return new Paginate<>(
                items,
                users.getNumber(),
                users.getTotalPages(),
                users.getTotalElements()
        );
    }
}
