package org.LoanService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {


    List<Loan> findById(UserDto userById);


    List<Loan> findByUserId(Integer userId);
}
