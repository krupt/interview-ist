package ru.ist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ist.model.User;
import ru.ist.repository.UserRepository;
import ru.ist.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User get(String name) {
		return userRepository.findByName(name);
	}

}
