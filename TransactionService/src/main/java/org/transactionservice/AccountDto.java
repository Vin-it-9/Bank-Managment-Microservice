package org.transactionservice;


public class AccountDto {

    private Integer id;
    private Double balance;
    private boolean status;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;

    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
