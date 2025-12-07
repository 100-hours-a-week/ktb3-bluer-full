package com.example.community.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Temporary encoder that accepts legacy plaintext passwords while new passwords are encoded.
 */
public class LegacyPasswordEncoder implements PasswordEncoder {

    private final PasswordEncoder delegate;

    public LegacyPasswordEncoder(PasswordEncoder delegate) {
        this.delegate = delegate;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return delegate.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword == null) {
            return false;
        }

        if (encodedPassword.startsWith("{")) {
            return delegate.matches(rawPassword, encodedPassword);
        }

        return encodedPassword.contentEquals(rawPassword);
    }
}
