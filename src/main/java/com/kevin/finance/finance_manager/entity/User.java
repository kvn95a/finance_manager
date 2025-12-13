package com.kevin.finance.finance_manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*; // import jakarta.persistence.Entity;
import java.time.Instant; // import java.time.Instant;

@Entity // Specify that this class is a JPA entity
@Table(name = "users") // Specify the table name
public class User {
    @Id // Specify the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Postgresql will need to use BIGINT for the ID field

    @Column(nullable = false, unique = true, length = 50) // Specify the column properties
    private String username;

    @JsonIgnore
    @Column(name = "password_hash", nullable = false, length = 100)
    private String passwordHash;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    /**
     * Default constructor for JPA
     * This constructor is required by JPA for entity instantiation.
     */
    public User() {
        // Default constructor for JPA
    }

    /**
     * Constructor to create a new User instance.
     * @param username the username of the user
     * @param passwordHash the hashed password of the user
     * @param email the email address of the user
     */
    public User(String username, String passwordHash, String email) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
    }

    /**
     * Get the ID of the user.
     * @return the ID of the user, it return a long data type.
     */
    public Long getId() {
        return id;
    }

    /**
     * The getUsername method returns the username of the user.
     * @return the username of the user, it return a string data type.
     */
    public String getUsername() { 
        return username; 
    }

    /**
     * The getPasswordHash method returns the hashed password of the user.
     * @return the hashed password of the user, it return a string data type.
     */
    public String getPasswordHash() { 
        return passwordHash; 
    }

    /**
     * The getEmail method returns the email address of the user.
     * @return the email address of the user, it return a string data type.
     */
    public String getEmail() { 
        return email; 
    }

    /**
     * The getCreatedAt method returns the creation timestamp of the user.
     * @return the creation timestamp of the user, it return a Instant data type.
     */
    public Instant getCreatedAt() { 
        return createdAt; 
    }

    /**
     * The setId method sets the ID of the user.
     * @param username the user name of the user
     */
    public void setUsername(String username) { 
        this.username = username; 
    }
    
    /**
     * The setPasswordHash method sets the hashed password of the user.
     * @param passwordHash the hashed password of the user
     */
    public void setPasswordHash(String passwordHash) { 
        this.passwordHash = passwordHash; 
    }

    /**
     * The setEmail method sets the email address of the user.
     * @param email the email address of the user
     */
    public void setEmail(String email) { 
        this.email = email; 
    }
}
