package org.accountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/user/{userId}")
    public Account createAccount(@PathVariable Integer userId, @RequestBody Account accountRequest) {
        return accountService.createAccountForUser(userId, accountRequest);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Account>> getAccountsByUserId(@PathVariable Integer userId) {
        List<Account> accounts = accountService.getAccountsByUserId(userId);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountId}")
    public Account getAccount(@PathVariable Integer accountId) {
        return accountService.getAccountById(accountId);
    }

    @GetMapping("/user/{userId}/{accountId}")
    public Account getAccountForUser(@PathVariable Integer userId, @PathVariable Integer accountId) {
        return accountService.getAccountForUser(userId, accountId);
    }


    @DeleteMapping("/delete/{accountId}")
    public void deleteAccount(@PathVariable Integer accountId) {
        accountService.deleteAccountById(accountId);
    }



}
