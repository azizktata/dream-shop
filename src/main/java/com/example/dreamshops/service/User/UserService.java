package com.example.dreamshops.service.User;

import com.example.dreamshops.dto.UserDto;
import com.example.dreamshops.exceptions.EntityNotFoundException;
import com.example.dreamshops.model.User;
import com.example.dreamshops.repository.UserRepo;
import com.example.dreamshops.request.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements IUserService{
    private final UserRepo userRepository;

    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(userRepository::delete, () -> {
            throw new EntityNotFoundException("User with id: " + id + " not found");
        });
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(UserDto::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id).map(UserDto::toDto).orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));
    }

    @Override
    public UserDto createUser(UserRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return UserDto.toDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return UserDto.toDto(userRepository.save(user));
    }


}
