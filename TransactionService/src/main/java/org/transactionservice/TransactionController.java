package org.transactionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/Transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

}
