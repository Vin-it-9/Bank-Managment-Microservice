package org.LoanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/loan")
public class LoanController {

    @Autowired
    LoanService loanService;


}
