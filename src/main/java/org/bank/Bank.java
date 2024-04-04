package org.bank;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, BankAccount> accounts;

    /** Constructor for Bank class */
    public Bank() {
        this.accounts = new HashMap<>();
    }

    /** Method to add an account to the bank */
    public synchronized void addAccount(BankAccount account) {
        accounts.put(account.getAccountNumber(), account);
    }

    /** Method to deposit money into a specific account */
    public synchronized void deposit(BankAccount account, double amount) {
        account.deposit(amount);
    }

    /** Method to withdraw money from a specific account */
    public synchronized void withdraw(BankAccount account, double amount) {
        if (account.getBalance() >= amount) {
            account.deposit(-amount); // Negative amount indicates withdrawal
            System.out.println("Withdrawal of " + amount + " made from account " + account.getAccountNumber() + ". New balance: " + account.getBalance());
        } else {
            System.out.println("Insufficient balance for withdrawal from account " + account.getAccountNumber() + ".");
        }
    }

    /** Method to check the balance of a specific account */
    public synchronized double checkBalance(BankAccount account) {
        return account.getBalance();
    }

    /** Method to request a loan installment for a specific account */
    public synchronized void requestLoanInstallment(BankAccount account, double monthlyInstallmentAmount) {
        account.requestLoanInstallment(monthlyInstallmentAmount);
    }
}
