package org.bank;

public class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    private boolean isLoanInstallmentDue;
    private double monthlyInstallmentAmount;

    /** Constructor for BankAccount class */
    public BankAccount(String accountNumber, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.isLoanInstallmentDue = false;
    }

    /** Method to deposit money into the account */
    public synchronized void deposit(double amount) {
        balance += amount;
        if (isLoanInstallmentDue && balance >= monthlyInstallmentAmount) {
            isLoanInstallmentDue = false;
            notifyAll(); // Notify bank to get the monthly installment
        }
        System.out.println("Deposit of " + amount + " made to account " + accountNumber + ". New balance: " + balance);
    }

    /** Method to request a loan installment */
    public synchronized void requestLoanInstallment(double monthlyInstallmentAmount) {
        this.monthlyInstallmentAmount = monthlyInstallmentAmount;
        if (balance < monthlyInstallmentAmount) {
            isLoanInstallmentDue = true;
            System.out.println("Monthly installment due for account " + accountNumber + ". Waiting for sufficient balance...");
            try {
                wait(); // Wait for sufficient balance to get the monthly installment
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        balance -= monthlyInstallmentAmount;
        System.out.println("Monthly installment of " + monthlyInstallmentAmount + " deducted from account " + accountNumber + ". New balance: " + balance);
    }
}
