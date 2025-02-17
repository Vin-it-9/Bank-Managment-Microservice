package org.LoanService;

import jakarta.transaction.Transactional;
import org.LoanService.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LoanService {

    private static final Integer BANK_ACCOUNT_ID = 0;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private AccountServiceClient accountServiceClient;

    @Autowired
    private TransactionServiceClient transactionServiceClient;


    public Loan applyForLoan(Loan loan) {

        UserDto user = userServiceClient.getUserById(loan.getUserId());

        if (user == null) {
            throw new UserNotFoundException("User not found with id: " + loan.getUserId());
        }

        AccountDto account = accountServiceClient.getAccountById(loan.getAccountId());

        if (account == null) {
            throw new AccountNotFoundException("Account not found with id: " + loan.getAccountId());
        }

        if (!account.getUserId().equals(loan.getUserId())) {
            throw new UnauthorizedAccessException("Account " + loan.getAccountId() +
                    " does not belong to user " + loan.getUserId());
        }

        if (loan.getStartDate() == null) {
            loan.setStartDate(LocalDateTime.now());
        }

        loan.setStatus(false);

        return loanRepository.save(loan);

    }

    public Optional<Loan> getLoanById(Integer id) {
        return loanRepository.findById(id);
    }

    public List<Loan> getLoanByUserId(Integer userId) {

        UserDto user = userServiceClient.getUserById(userId);

        if (user == null) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        List<Loan> loans = loanRepository.findByUserId(userId);

        if (loans == null || loans.isEmpty()) {
            throw new LoanNotFoundException("No loans found for user id: " + userId);
        }

        return loans;
    }


    public Loan approveLoan(Integer loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with id: " + loanId));

        if (loan.isApproved()) {
            throw new IllegalStateException("Loan is already approved.");
        }

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setSenderAccountId(BANK_ACCOUNT_ID);
        transactionRequest.setReceiverAccountId(loan.getAccountId());
        transactionRequest.setAmount(loan.getAmount());
        transactionRequest.setDescription("Disbursement for loan id " + loanId);

        TransactionResponse txResponse = transactionServiceClient.createTransaction(transactionRequest);

        if (!txResponse.isStatus()) {
            throw new RuntimeException("Transaction failed: " + txResponse.getMessage());
        }

        loan.setApproved(true);
        return loanRepository.save(loan);
    }






}
