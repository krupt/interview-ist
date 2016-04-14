package ru.ist.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class Security {

    public static final long SYSTEM_USER_ID = 0;

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_MANAGER = "ROLE_MANAGER";
    public static final String ROLE_CHIEF = "ROLE_CHIEF";

    public static final GrantedAuthority AUTHORITY_ROLE_ADMIN = new SimpleGrantedAuthority(ROLE_ADMIN);
    public static final GrantedAuthority AUTHORITY_ROLE_USER = new SimpleGrantedAuthority(ROLE_USER);
    public static final GrantedAuthority AUTHORITY_ROLE_MANAGER = new SimpleGrantedAuthority(ROLE_MANAGER);
    public static final GrantedAuthority AUTHORITY_ROLE_CHIEF = new SimpleGrantedAuthority(ROLE_CHIEF);

    public static CurrentUser getCurrentUser() {
        CurrentUser user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CurrentUser)
                user = (CurrentUser) principal;
        }
        return user;
    }

    public static long getCurrentUserId() {
        CurrentUser currentUser = getCurrentUser();
        if (Objects.nonNull(currentUser)) {
            return currentUser.getId();
        }
        return SYSTEM_USER_ID;
    }

}
