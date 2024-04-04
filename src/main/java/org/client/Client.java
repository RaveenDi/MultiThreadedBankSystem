package org.client;

import org.bank.Bank;
import org.bank.BankAccount;

public class Client extends Thread {
    private Bank bank;
    private BankAccount account;

    /** Constructor for Client class */
    public Client(Bank bank, BankAccount account) {
        this.bank = bank;
        this.account = account;
    }

    /** Run method to execute banking operations */
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started.");

        // Simulate multiple banking operations
        for (int i = 0; i < 5; i++) {
            double amount = Math.random() * 1000; // Random amount for deposit or withdrawal
            int operation = (int) (Math.random() * 4); // Random operation: 0 = deposit, 1 = withdraw, 2 = check balance, 3 = request loan installment

            switch (operation) {
                case 0:
                    bank.deposit(account, amount);
                    break;
                case 1:
                    bank.withdraw(account, amount);
                    break;
                case 2:
                    double balance = bank.checkBalance(account);
                    System.out.println("Balance of account " + account.getAccountNumber() + ": " + balance);
                    break;
                case 3:
                    bank.requestLoanInstallment(account, 500); // Request a loan installment of $500
                    break;
            }

            try {
                Thread.sleep((long) (Math.random() * 1000)); // Sleep for random time before next operation
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(Thread.currentThread().getName() + " finished.");
    }
}
