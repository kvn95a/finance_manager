package com.kevin.finance.finance_manager.entity;

import jakarta.persistence.*; 
import java.math.BigDecimal;
import java.time.Instant;

/**
 * The Account class represents a financial account associated with a user.
 * It contains information about the account such as its name, type, starting balance, and creation timestamp.
 */

@Entity // Specify that this class is a JPA entity
@Table(name = "accounts") // Specify the table name
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(name = "starting_balance", nullable = false, precision = 12, scale = 2)
    private BigDecimal startingBalance = BigDecimal.ZERO;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    /**
     *  Default constructor for JPA
     *  This constructor is required by JPA for entity instantiation.
     */
    public Account() {
        // Default constructor for JPA
    }

    /**
     *  Constructor to create a new Account instance.
     *  This constructor initializes the account with a user, name, type, and starting balance
     * @param user The user associated with the account
     * @param name 
     * @param type the type of the account (e.g., "savings", "checking")
     * @param startingBalance  the initial balance of the account
     */
    public Account(User user, String name, String type, BigDecimal startingBalance) {
        this.user = user;
        this.name = name;
        this.type = type;
        this.startingBalance = startingBalance;
    }

    /**
     * The getId method returns the ID of the account.
     * This method is used to retrieve the unique identifier of the account.
     * @return  the ID of the account, it returns a Long data type.
     */
    public Long getId() { 
        return id; 
    }
    
    /**
     *  The getUser method returns the user associated with the account.
     *  This method is used to retrieve the user who owns the account.
     * @return the user associated with the account, it returns a User object.
     */
    public User getUser() { 
        return user; 
    }

    /**
     *  The getName method returns the name of the account.
     * @return the name of the account, it returns a String data type.
     */
    public String getName() { 
        return name; 
    }

    /**
     *  The getType method returns the type of the account.
     * @return the type of the account, it returns a String data type.
     */
    public String getType() { 
        return type; 
    }

    /**
     *  The getStartingBalance method returns the starting balance of the account.
     * @return the starting balance of the account, it returns a BigDecimal data type.
     */
    public BigDecimal getStartingBalance() { 
        return startingBalance; 
    }

    /**
     *  The getCreatedAt method returns the creation timestamp of the account.
     * @return the creation timestamp of the account, it returns an Instant data type.
     */
    public Instant getCreatedAt() { 
        return createdAt; 
    }

    /**
     *  The setUser method sets the user associated with the account.
     * @param user the user to associate with the account
     */
    public void setUser(User user) { 
        this.user = user; 
    }

    /**
     *  The setName method sets the name of the account.
     * @param name the name of the account
     */
    public void setName(String name) { 
        this.name = name; 
    }

    /**
     *  The setType method sets the type of the account.
     * @param type  the type of the account (e.g., "savings", "checking")
     */
    public void setType(String type) { 
        this.type = type; 
    }

    /**
     *  The setStartingBalance method sets the starting balance of the account.
     * @param startingBalance the initial balance of the account
     */
    public void setStartingBalance(BigDecimal startingBalance) { 
        this.startingBalance = startingBalance; 
    }
}
