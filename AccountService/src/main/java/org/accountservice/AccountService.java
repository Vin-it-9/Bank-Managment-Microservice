package org.accountservice;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountService {

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

        // Set the userId and then save the account.
        accountRequest.setUserId(userId);
        return accountRepository.save(accountRequest);
    }






}
