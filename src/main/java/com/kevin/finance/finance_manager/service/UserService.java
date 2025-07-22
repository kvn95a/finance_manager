package com.kevin.finance.finance_manager.service;

import com.kevin.finance.finance_manager.entity.User;
import com.kevin.finance.finance_manager.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service; // For service annotation

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructor for UserService.
     * Initializes the user repository and password encoder.
     * @param userRepo the repository for user operations 
     */
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * The register Method Registers a new user, it also performs the following:
     * - Checks for existing username/email.
     * - Hashes the raw password.
     * - Persists the User.
     * @param username the desired username
     * @param rawPassword the raw password to be hashed
     * @param email the user's email address
     * @return the saved User entity or throws an exception if username or email already exists.
     * @throws RuntimeException if username or email already exists.
     */
    public User register(String username, String rawPassword, String email) {
        if (userRepo.existsByUsername(username)) {
            throw new RuntimeException("Username already taken");
        }
        if (userRepo.existsByEmail(email)) {
            throw new RuntimeException("Email already in use");
        }
        String hashed = passwordEncoder.encode(rawPassword);
        User user = new User(username, hashed, email);
        return userRepo.save(user);
    }

    /**
     * Finds a user by username.
     * Useful for authentication.
     * @param username the username of the user to find
     * @return an Optional containing the User if found, or empty if not found.
     */
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
