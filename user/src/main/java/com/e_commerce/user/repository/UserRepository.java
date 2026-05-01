package com.e_commerce.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}