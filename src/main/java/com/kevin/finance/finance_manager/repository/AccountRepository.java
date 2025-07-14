package com.kevin.finance.finance_manager.repository;

import com.kevin.finance.finance_manager.entity.Account;
import com.kevin.finance.finance_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * The AccountRepository interface extends JpaRepository to provide CRUD operations for Account entities.
 * It allows for easy interaction with the database without needing to write boilerplate code or SQL queries.
 * The Account parameter specifies the entity type, and Long is the type of the entity's primary
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * The findAllByUser method retrieves all accounts associated with a specific user.
     * @param user the user whose accounts are to be retrieved
     * @return a list of accounts associated with the specified user
     */
    List<Account> findAllByUser(User user);
}
