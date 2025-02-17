package org.accountservice;


public class AccountBalanceDto {
    private Double balance;

    public AccountBalanceDto(Double balance) {
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
