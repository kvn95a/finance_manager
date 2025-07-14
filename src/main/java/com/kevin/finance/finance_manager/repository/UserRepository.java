package com.kevin.finance.finance_manager.repository;

import com.kevin.finance.finance_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * UserRepository is an interface that extends JpaRepository to provide CRUD operations for User entities.
 * The idea is to allow for easy interaction with the database without needing to write boilerplate code or SQL queries.
 * The User parameter specifies the entity type, and Long is the type of the entity's primary key.
 */

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find a user by their username.
     * This method retrieves a user from the database based on their username.
     * @param username the username of the user to find
     * @return an Optional containing the User if found, or empty if not found
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Check if a user exists by their username.
     * This method checks if a user with the given username exists in the database.
     * @param username the username to check
     * @return true if a user with the given username exists, false otherwise
     */
    boolean existsByUsername(String username);
    
    /**
     * Check if a user exists by their email.
     * This method checks if a user with the given email exists in the database.
     * @param email the email to check
     * @return true if a user with the given email exists, false otherwise
     */
    boolean existsByEmail(String email);
}
