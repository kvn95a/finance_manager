package com.kevin.finance.finance_manager.controller;

import com.kevin.finance.finance_manager.dto.AccountResponse;
import com.kevin.finance.finance_manager.entity.Account;
import com.kevin.finance.finance_manager.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public static class CreateAccountRequest {
        public Long userId;
        public String name;
        public String type;
        public BigDecimal startingBalance;
    }

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateAccountRequest req) {
        try {
            Account acct = accountService.createForUser(
                req.userId, req.name, req.type, req.startingBalance
            );
            return ResponseEntity.ok(toResponse(acct));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> listByUser(@PathVariable Long userId) {
        try {
            List<Account> accounts = accountService.listByUser(userId);
            List<AccountResponse> responses = accounts.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    private AccountResponse toResponse(Account account) {
        return new AccountResponse(
            account.getId(),
            account.getUser().getId(),
            account.getName(),
            account.getType(),
            account.getStartingBalance(),
            account.getCreatedAt()
        );
    }
}
