package com.example.dreamshops.service.Auth;

import com.example.dreamshops.dto.AuthDto;
import com.example.dreamshops.dto.UserDto;
import com.example.dreamshops.model.User;
import com.example.dreamshops.repository.UserRepo;
import com.example.dreamshops.request.LoginRequest;
import com.example.dreamshops.request.UserRequest;
import com.example.dreamshops.security.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UserDto register(UserRequest request) {

        Optional.ofNullable(userRepo.findByEmail(request.getEmail())).ifPresent(u -> {
            throw new IllegalStateException("Email already taken");
        });
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        return UserDto.toDto(userRepo.save(user));
    }

    public AuthDto login(LoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(authentication);
        User user = (User) authentication.getPrincipal();

        return new AuthDto(user.getId(),token);
    }
}
