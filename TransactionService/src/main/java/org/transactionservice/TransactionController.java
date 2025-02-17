package org.transactionservice;

import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/{id}")
    public Transaction getTransaction(@PathVariable Integer id) {
        return transactionService.getTransaction(id);
    }

    @GetMapping("/")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }


    @PostMapping("/send")
    public Transaction createTransaction(@RequestBody TransactionRequest request) {
        return transactionService.createTransaction(
                request.getSenderAccountId(),
                request.getReceiverAccountId(),
                request.getAmount(),
                request.getDescription()
        );
    }

    @PostMapping("/sendFromLoan")
    public Transaction createTransactionLoan(@RequestBody TransactionRequest request) {
        return transactionService.createTransactionLoan(
                request.getReceiverAccountId(),
                request.getAmount(),
                request.getDescription()
        );
    }

    @GetMapping("/account/{accountId}")
    public List<Transaction> getTransactionsByAccountId(@PathVariable Integer accountId) {
        return transactionService.getTransactionsByAccountId(accountId);
    }

    @GetMapping("/{accountId}/sent")
    public List<Transaction> getSentTransactions(@PathVariable Integer accountId) {
        return transactionService.getSentTransactionsByAccountId(accountId);
    }

    @GetMapping("/{accountId}/received")
    public List<Transaction> getReceivedTransactions(@PathVariable Integer accountId) {
        return transactionService.getReceivedTransactionsByAccountId(accountId);
    }



}
