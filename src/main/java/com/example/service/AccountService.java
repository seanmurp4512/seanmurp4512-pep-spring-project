package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().trim().isEmpty() ||
                account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount.isPresent()) {
            throw new IllegalStateException("Username already exists");
        }

        return accountRepository.save(account);
    }

    public Account loginAccount(Account account){
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid username");
        }
        Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());
        if (!existingAccount.isPresent()) {
            throw new IllegalArgumentException("Username does not exist");
        }
        if (!existingAccount.get().getPassword().equals(account.getPassword())){
            throw new IllegalArgumentException("Incorrect Password");
        }
        return existingAccount.get();
    }
}
