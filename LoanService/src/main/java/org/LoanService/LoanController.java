package org.LoanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    LoanService loanService;


    @PostMapping("/apply")
    public Loan applyForLoan(@RequestBody Loan loan) {
        return loanService.applyForLoan(loan);
    }



}
