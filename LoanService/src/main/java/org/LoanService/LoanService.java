package org.LoanService;

import jakarta.transaction.Transactional;
import org.LoanService.exception.AccountNotFoundException;
import org.LoanService.exception.UnauthorizedAccessException;
import org.LoanService.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    private UserServiceClient userServiceClient;       // Feign client to fetch user details

    @Autowired
    private AccountServiceClient accountServiceClient; // Feign client to fetch account details


    public Loan applyForLoan(Loan loan) {
        // Validate that the user exists.
        UserDto user = userServiceClient.getUserById(loan.getUserId());

        if (user == null) {
            throw new UserNotFoundException("User not found with id: " + loan.getUserId());
        }

        // Validate that the account exists.
        AccountDto account = accountServiceClient.getAccountById(loan.getAccountId());

        if (account == null) {
            throw new AccountNotFoundException("Account not found with id: " + loan.getAccountId());
        }

        // Check that the account belongs to the user.
        if (!account.getUserId().equals(loan.getUserId())) {
            throw new UnauthorizedAccessException("Account " + loan.getAccountId() +
                    " does not belong to user " + loan.getUserId());
        }

        // Set default startDate if not provided.
        if (loan.getStartDate() == null) {
            loan.setStartDate(LocalDateTime.now());
        }

        // Set default status to false (pending) if desired.
        loan.setStatus(false);

        return loanRepository.save(loan);

    }






}
