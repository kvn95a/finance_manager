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
     * Creates a new account for the authenticated username.
     * @param username the authenticated username
     * @param name the name of the account
     * @param type the type of the account (e.g., savings, checking)
     * @param startingBalance the initial balance of the account
     * @return the created Account entity.
     */
    public Account createForUsername(String username, String name, String type, BigDecimal startingBalance) {
        User user = findByUsernameOrThrow(username);
        Account acct = new Account(user, name, type, startingBalance);
        return accountRepo.save(acct);
    }

    /**
     * Lists all accounts belonging to the authenticated username.
     * @param username the authenticated username
     * @return a list of Account entities associated with the user.
     */
    public List<Account> listByUsername(String username) {
        User user = findByUsernameOrThrow(username);
        return accountRepo.findAllByUser(user);
    }

    private User findByUsernameOrThrow(String username) {
        return userRepo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
}
