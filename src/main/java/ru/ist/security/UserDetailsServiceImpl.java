package ru.ist.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ist.model.User;
import ru.ist.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@Service("userDetailsService")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserService userService;

	@Autowired
	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.get(username);
		if (null == user) {
			log.warn("Пользователь '{}' не найден в БД", username);
			throw new UsernameNotFoundException(String.format("%s not found", username));
		}
		Set<GrantedAuthority> authorities = user.getRoles()
			.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
					.collect(Collectors.toSet());
		CurrentUser currentUser = new CurrentUser(
            user.getId(),
            user.getName(),
            user.getPassword(),
            user.getEmployee().getId(),
            user.getEmployee().getDescription(),
            user.isEnabled(),
            true,
            true,
            true,
            authorities
        );
		log.debug("Пользователь найден: \n{}", currentUser);
		return currentUser;
	}

}
