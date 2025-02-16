package org.transactionservice;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;



}
