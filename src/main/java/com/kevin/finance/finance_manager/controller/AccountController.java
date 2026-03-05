package com.kevin.finance.finance_manager.controller;

import com.kevin.finance.finance_manager.dto.AccountResponse;
import com.kevin.finance.finance_manager.entity.Account;
import com.kevin.finance.finance_manager.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Account API endpoints scoped to the authenticated user.
 * Client input never controls account ownership.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    /**
     * Request payload for creating an account owned by the authenticated user.
     */
    public static class CreateAccountRequest {
        /** Display name for the account (for example: "Checking"). */
        public String name;
        /** Business type/category of account (for example: "checking", "savings"). */
        public String type;
        /** Initial balance at creation time. */
        public BigDecimal startingBalance;
    }

    /**
     * @param accountService account business operations scoped by authenticated username
     */
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Creates an account for the authenticated user.
     *
     * @param req request payload with account details
     * @param principal authenticated user principal injected by Spring Security
     * @return 200 with created account payload, or 401 when principal is missing
     */
    @PostMapping
    public ResponseEntity<AccountResponse> create(
            @RequestBody CreateAccountRequest req,
            @AuthenticationPrincipal UserDetails principal
    ) {
        // Defensive guard; endpoint should be authenticated by security config.
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }

        Account acct = accountService.createForUsername(
                principal.getUsername(), req.name, req.type, req.startingBalance
        );

        return ResponseEntity.ok(toResponse(acct));
    }

    /**
     * Lists accounts owned by the authenticated user.
     *
     * @param principal authenticated user principal injected by Spring Security
     * @return 200 with list of account payloads, or 401 when principal is missing
     */
    @GetMapping
    public ResponseEntity<List<AccountResponse>> listMine(
            @AuthenticationPrincipal UserDetails principal
    ) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }

        List<AccountResponse> responses = accountService.listByUsername(principal.getUsername())
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    /**
     * Maps domain entity to API response DTO.
     *
     * @param account persisted account entity
     * @return serialized response model
     */
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
