package org.LoanService;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@FeignClient(name = "AccountService" , path = "/account")
public interface AccountServiceClient {

    @GetMapping("/{accountId}")
    AccountDto getAccountById(@PathVariable("accountId") Integer accountId);


    @PutMapping("/{accountId}/deduct/{amount}")
    void deductBalance(@PathVariable Integer accountId, @PathVariable Double amount);

    @PutMapping("/{accountId}/add/{amount}")
    void addBalance(@PathVariable Integer accountId, @PathVariable Double amount);


}
