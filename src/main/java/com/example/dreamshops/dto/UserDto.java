package com.example.dreamshops.dto;

import com.example.dreamshops.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private CartDto cart;

    public UserDto(Long id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public static UserDto toDto(User entity){
       UserDto userDto = new UserDto(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPassword());
         if(entity.getCart() != null){
              userDto.setCart(CartDto.toDto(entity.getCart()));
         }
         return userDto;
    }
}
