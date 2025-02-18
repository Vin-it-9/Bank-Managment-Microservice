package org.transactionservice;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.transactionservice.exception.AccountNotFoundException;
import org.transactionservice.exception.InsufficientBalanceException;
import org.transactionservice.exception.TransactionNotFoundException;

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

        accountServiceClient.deductBalance(senderAccountId, amount);

        accountServiceClient.addBalance(receiverAccountId, amount);

        Transaction transaction = new Transaction();
        transaction.setSenderAccountId(senderAccountId);
        transaction.setReceiverAccountId(receiverAccountId);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setDate(LocalDateTime.now());
        transaction.setStatus(true);

        return transactionRepository.save(transaction);

    }

    public Transaction createTransactionLoan(Integer receiverAccountId, Double amount, String description) {

        AccountDto receiverAccount = accountServiceClient.getAccountById(receiverAccountId);

        if (receiverAccount == null) {
            throw new AccountNotFoundException("Receiver account not found with ID: " + receiverAccountId);
        }

        accountServiceClient.addBalance(receiverAccountId, amount);

        Transaction transaction = new Transaction();
        transaction.setSenderAccountId(0);
        transaction.setReceiverAccountId(receiverAccountId);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setDate(LocalDateTime.now());
        transaction.setStatus(true);

        return transactionRepository.save(transaction);

    }


    public List<Transaction> getTransactionsByAccountId(Integer accountId) {
        List<Transaction> transactions = transactionRepository.findBySenderAccountIdOrReceiverAccountId(accountId, accountId);

        if (transactions.isEmpty()) {
            throw new TransactionNotFoundException("No transactions found for account ID: " + accountId);
        }
        return transactions;
    }

    public List<Transaction> getSentTransactionsByAccountId(Integer accountId) {
        List<Transaction> transactions = transactionRepository.findBySenderAccountId(accountId);
        if (transactions.isEmpty()) {
            throw new TransactionNotFoundException("No sent transactions found for account ID: " + accountId);
        }
        return transactions;
    }

    public List<Transaction> getReceivedTransactionsByAccountId(Integer accountId) {
        List<Transaction> transactions = transactionRepository.findByReceiverAccountId(accountId);
        if (transactions.isEmpty()) {
            throw new TransactionNotFoundException("No received transactions found for account ID: " + accountId);
        }
        return transactions;
    }



}
