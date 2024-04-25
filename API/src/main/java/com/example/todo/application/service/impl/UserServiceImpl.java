package com.example.todo.application.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.todo.application.constants.ErrorCodes;
import com.example.todo.application.exception.BadRequestException;
import com.example.todo.application.form.UserForm;
import com.example.todo.application.model.User;
import com.example.todo.application.repository.UserRepository;
import com.example.todo.application.security.JwtTokenProvider;
import com.example.todo.application.service.UserService;
import com.example.todo.application.util.LanguageUtil;

@Service
public class UserServiceImpl implements UserService {
    @Value("${global.language}")
    private String globalLanguage;

    private final LanguageUtil languageUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserServiceImpl(LanguageUtil languageUtil, UserRepository userRepository, PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider) {
        this.languageUtil = languageUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public User signUp(UserForm userForm) {
        // Check if username exists
        if (userRepository.findByUsername(userForm.getUsername()) != null) {
            throw new BadRequestException(
                    languageUtil.getTranslatedText(ErrorCodes.EXISTING_USER, null, globalLanguage));
        }

        if (userRepository.findByEmail(userForm.getEmail()) != null) {
            throw new BadRequestException(
                    languageUtil.getTranslatedText(ErrorCodes.EXISTING_USER, null, globalLanguage));
        }

        // Create a new user
        User newUser = new User(userForm.getUsername(), userForm.getEmail(),
                passwordEncoder.encode(userForm.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public String login(String email, String password) {
        // Authenticate the user
        // Implement your authentication logic here, such as checking the database for
        // the user's credentials
        // For simplicity, let's assume the user exists and the password matches
        User user = userRepository.findByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException(languageUtil.getTranslatedText(ErrorCodes.LOGIN_ERROR, null, globalLanguage));
        }

        // Generate JWT token
        return jwtTokenProvider.createToken(email);
    }
}