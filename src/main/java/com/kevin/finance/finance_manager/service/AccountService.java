package com.kevin.finance.finance_manager.service;

import com.kevin.finance.finance_manager.entity.Account;
import com.kevin.finance.finance_manager.entity.User;
import com.kevin.finance.finance_manager.repository.AccountRepository;
import com.kevin.finance.finance_manager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepo;
    private final UserRepository userRepo;

    /**
     * Constructor for AccountService.
     * Initializes the repositories.
     * @param accountRepo the repository for account operations
     * @param userRepo the repository for user operations
     */
    public AccountService(AccountRepository accountRepo, UserRepository userRepo) {
        this.accountRepo = accountRepo;
        this.userRepo = userRepo;
    }

    /**
     * The createForUser method creates a new account for the user with the given ID.
     * It performs the following:
     * - Checks if the user exists.
     * - Creates a new Account entity.
     * - Saves the Account entity to the repository.
     * @param userId the ID of the user for whom the account is being created
     * @param name the name of the account
     * @param type the type of the account (e.g., savings, checking)
     * @param startingBalance the initial balance of the account
     * @return the created Account entity.
     * @throws RuntimeException if the user does not exist.
     */
    public Account createForUser(Long userId, String name, String type, BigDecimal startingBalance) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Account acct = new Account(user, name, type, startingBalance);
        return accountRepo.save(acct);
    }

    /**
     * Lists all accounts belonging to the given user ID.
     * Throws if the user doesn’t exist.
     * @param userId the ID of the user whose accounts are to be listed
     * @return a list of Account entities associated with the user.
     * @throws RuntimeException if the user does not exist.
     */
    public List<Account> listByUser(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return accountRepo.findAllByUser(user);
    }
}