package org.LoanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    LoanService loanService;


    @PostMapping("/apply")
    public Loan applyForLoan(@RequestBody Loan loan) {
        return loanService.applyForLoan(loan);
    }

    @GetMapping("/{id}")
    public Optional<Loan> getLoanById(@PathVariable Integer id) {
        return loanService.getLoanById(id);
    }

    @GetMapping("/user/{id}")
    public List<Loan> getLoanByUserId(@PathVariable Integer id) {
        return loanService.getLoanByUserId(id);
    }

    @PutMapping("/approve/{loanId}")
    public ResponseEntity<Loan> approveLoan(@PathVariable Integer loanId) {
        Loan updatedLoan = loanService.approveLoan(loanId);
        return ResponseEntity.ok(updatedLoan);
    }

    @GetMapping("/{loanId}/repayment")
    public ResponseEntity<?> getRepaymentAmount(@PathVariable Integer loanId) {
        try {
            Double repaymentAmount = loanService.calculateAndUpdateRepaymentAmount(loanId);
            return ResponseEntity.ok(repaymentAmount);
        } catch (IllegalStateException ex) {
            return ResponseEntity.ok().body(ex.getMessage());
        }
    }

    @PutMapping("/{loanId}/pay")
    public ResponseEntity<?> payLoan(@PathVariable Integer loanId) {
        try {
            String result = loanService.payLoanAmount(loanId);
            return ResponseEntity.ok(result);
        } catch ( RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }





}
