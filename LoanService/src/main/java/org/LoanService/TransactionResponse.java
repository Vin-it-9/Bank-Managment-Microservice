package org.LoanService;

public class TransactionResponse {

    private boolean status;
    private String message;

    // Getters and setters

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
