package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    /**
     * Find an account by username.
     * @param username the username of the account
     * @return Optional containing the account if found, otherwise empty
     */
    Optional<Account> findByUsername(String username);
}
