package org.accountservice;


import jakarta.transaction.Transactional;
import org.accountservice.exception.AccountNotFoundException;
import org.accountservice.exception.UnauthorizedAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class AccountService {

    @Autowired
    private final AccountRepository accountRepository;

    private final UserServiceClient userServiceClient;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserServiceClient userServiceClient) {
        this.accountRepository = accountRepository;
        this.userServiceClient = userServiceClient;
    }

    public Account createAccountForUser(Integer userId, Account accountRequest) {

        UserDto user = userServiceClient.getUserById(userId);

        if (user == null) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        accountRequest.setUserId(userId);
        accountRequest.setAccountNumber(generateUniqueAccountNumber());
        return accountRepository.save(accountRequest);

    }

    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            accountNumber = generateRandom12DigitNumber();
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    private String generateRandom12DigitNumber() {
        long number = ThreadLocalRandom.current().nextLong(0, 1000000000000L);
        return String.format("%012d", number);
    }

    public Account getAccountById(Integer accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + accountId));
    }

    public List<Account> getAccountsByUserId(Integer userId) {
        List<Account> accounts = accountRepository.findByUserId(userId);
        if (accounts.isEmpty()) {
            throw new AccountNotFoundException("No accounts found for user with ID: " + userId);
        }
        return accounts;
    }

    public void deleteAccountById(Integer accountId) {
        if(accountRepository.existsById(accountId)) {
            accountRepository.deleteById(accountId);
        }
        else {
            throw new AccountNotFoundException("Account not found with id: " + accountId);
        }
    }

    public Account getAccountForUser(Integer userId, Integer accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + accountId));
        if (!account.getUserId().equals(userId)) {
            throw new UnauthorizedAccessException("User with id " + userId + " is not authorized to access this account");
        }
        return account;
    }




}
