package ru.ist.security;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertTrue;

public class GeneratePasswordTest {

    @Test
    public void generate() {
        String encodedPassword = new BCryptPasswordEncoder().encode("12345");
        System.out.println(encodedPassword);
        assertTrue(encodedPassword.startsWith("$2a$10$"));
    }

}
