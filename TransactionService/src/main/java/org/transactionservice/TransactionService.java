package org.transactionservice;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.transactionservice.exception.AccountNotFoundException;
import org.transactionservice.exception.InsufficientBalanceException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private AccountServiceClient accountServiceClient;


    public Transaction getTransaction(Integer id) {
        return transactionRepository.findById(id).get();
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction createTransaction(Integer senderAccountId, Integer receiverAccountId, Double amount, String description) {

        AccountDto senderAccount = accountServiceClient.getAccountById(senderAccountId);
        if (senderAccount == null) {
            throw new AccountNotFoundException("Sender account not found with ID: " + senderAccountId);
        }

        AccountDto receiverAccount = accountServiceClient.getAccountById(receiverAccountId);
        if (receiverAccount == null) {
            throw new AccountNotFoundException("Receiver account not found with ID: " + receiverAccountId);
        }

        if (senderAccount.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance in sender's account");
        }

        // Deduct balance from sender
        accountServiceClient.deductBalance(senderAccountId, amount);

        // Add balance to receiver
        accountServiceClient.addBalance(receiverAccountId, amount);

        // Create and save transaction
        Transaction transaction = new Transaction();
        transaction.setSenderAccountId(senderAccountId);
        transaction.setReceiverAccountId(receiverAccountId);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setDate(LocalDateTime.now());
        transaction.setStatus(true);

        return transactionRepository.save(transaction);

    }



}
