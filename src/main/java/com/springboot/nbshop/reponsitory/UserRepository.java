package com.springboot.nbshop.reponsitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.nbshop.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findUserByEmail(String email);

}
