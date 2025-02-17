package org.LoanService;

import org.springframework.beans.factory.annotation.Autowired;
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




}
