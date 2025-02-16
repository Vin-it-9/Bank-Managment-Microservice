package org.accountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Endpoint to create an account for a given user.
    @PostMapping("/user/{userId}")
    public Account createAccount(@PathVariable Integer userId, @RequestBody Account accountRequest) {
        return accountService.createAccountForUser(userId, accountRequest);
    }



}
