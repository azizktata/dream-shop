package com.example.dreamshops.service.User;

import com.example.dreamshops.dto.UserDto;
import com.example.dreamshops.model.User;
import com.example.dreamshops.request.UserRequest;

import java.util.List;

public interface IUserService {
    void deleteUser(Long id);
    List<UserDto> getUsers();
    UserDto getUserById(Long id);
    UserDto createUser(UserRequest request);
    UserDto updateUser(Long id, UserRequest request);
}
