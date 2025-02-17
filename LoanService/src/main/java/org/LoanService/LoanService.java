package org.LoanService;

import jakarta.transaction.Transactional;
import org.LoanService.exception.AccountNotFoundException;
import org.LoanService.exception.LoanNotFoundException;
import org.LoanService.exception.UnauthorizedAccessException;
import org.LoanService.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

        // Retrieve loans for the user.
        List<Loan> loans = loanRepository.findByUserId(userId);

        if (loans == null || loans.isEmpty()) {
            throw new LoanNotFoundException("No loans found for user id: " + userId);
        }

        return loans;
    }





}
