package com.kevin.finance.finance_manager.dto;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO returned by the account API so we avoid leaking internal entity state.
 */
public record AccountResponse(
    Long id,
    Long userId,
    String name,
    String type,
    BigDecimal startingBalance,
    Instant createdAt
) { }
