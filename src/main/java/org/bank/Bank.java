package org.bank;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Bank {
    private Map<String, BankAccount> accounts;
    private Random random;

    /** Constructor to initialize the bank */
    public Bank() {
        accounts = new HashMap<>();
        random = new Random();
    }

    /** Method to add a new account to the bank */
    public synchronized void addAccount(BankAccount account) {
        accounts.put(account.getAccountNumber(), account);
    }

    /** Method to get an account by account number */
    public synchronized BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    /** Method to handle monthly loan installment for a random account */
    public void handleLoanInstallment(double installmentAmount) {
        // Randomly select two accounts
        String[] accountNumbers = accounts.keySet().toArray(new String[0]);
        String accountNumber1 = accountNumbers[random.nextInt(accountNumbers.length)];
        String accountNumber2;
        do {
            accountNumber2 = accountNumbers[random.nextInt(accountNumbers.length)];
        } while (accountNumber2.equals(accountNumber1)); // Ensure two distinct accounts

        // Deduct the installment from the selected accounts
        BankAccount account1 = accounts.get(accountNumber1);
        BankAccount account2 = accounts.get(accountNumber2);

        if (account1 != null && account2 != null) {
            account1.handleLoanInstallment(installmentAmount);
            account2.handleLoanInstallment(installmentAmount);
        } else {
            System.out.println("[Bank] Error: One or both accounts not found.");
        }
    }
}
