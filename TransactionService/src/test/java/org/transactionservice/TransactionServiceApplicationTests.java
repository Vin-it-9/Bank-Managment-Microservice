package org.transactionservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DataJpaTest
class TransactionServiceApplicationTests {


    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void contextLoads() {
    }

}
