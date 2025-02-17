package org.LoanService;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "TransactionService" , path = "/transaction")
public interface TransactionServiceClient {

    @PostMapping("/sendFromLoan")
    TransactionResponse createTransaction(@RequestBody TransactionRequest request);


}
