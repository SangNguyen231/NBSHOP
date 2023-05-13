package com.springboot.nbshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springboot.nbshop.entity.User;

@Service
public interface UserService {
	List<User> getAllUser();

	void updateUser(User user);

	void removeUserById(int id);

	Optional<User> getUserById(int id);

	Optional<User> getUserByEmail(String email);
}
