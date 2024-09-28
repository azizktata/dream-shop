package com.example.dreamshops.request;

import com.example.dreamshops.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Integer phoneNumber;
    private String region;
    private String city;
    private String address;
    private String password;


}
