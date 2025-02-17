package org.transactionservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findBySenderAccountIdOrReceiverAccountId(Integer senderAccountId, Integer receiverAccountId);


    List<Transaction> findBySenderAccountId(Integer senderAccountId);

    List<Transaction> findByReceiverAccountId(Integer receiverAccountId);


}
