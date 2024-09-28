package com.example.dreamshops.dto;

import com.example.dreamshops.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {
    private Long id;
    private String token;
    private String firstName;
    private String lastName;
    private String email;
    private Integer phoneNumber;
    private String region;
    private String city;
    private String address;

    public AuthDto(Long id, String firstName, String lastName, String email, Integer phoneNumber, String region, String city, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.region = region;
        this.city = city;
        this.address = address;
    }


    public static AuthDto userToAuthDto (User user){
        return new AuthDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(), user.getRegion(), user.getCity(), user.getAddress());
    }
}
