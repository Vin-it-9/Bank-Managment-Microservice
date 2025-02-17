package org.LoanService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class LoanCalculator {

    public static double calculateTotalRepayment(Loan loan) {
        LocalDateTime now = LocalDateTime.now();
        long daysElapsed = ChronoUnit.DAYS.between(loan.getStartDate(), now);
        double yearsElapsed = daysElapsed / 365.0;  // approximate conversion
        double interestAccrued = loan.getAmount() * loan.getInterestRate() * yearsElapsed;
        return loan.getAmount() + interestAccrued;
    }


}
