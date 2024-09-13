package com.example.dreamshops.repository;

import com.example.dreamshops.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
