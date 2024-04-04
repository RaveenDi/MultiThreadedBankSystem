package org;

import org.bank.Bank;
import org.bank.BankAccount;
import org.client.Client;

public class Main {
    public static void main(String[] args) {
        // Instantiate Bank and create BankAccounts
        Bank bank = new Bank();
        BankAccount[] accounts = new BankAccount[10];
        for (int i = 0; i < accounts.length; i++) {
            BankAccount account = new BankAccount(String.valueOf(i), "Holder " + i, 1000);
            bank.addAccount(account);
            accounts[i] = account;
        }

        // Create and start at least 10 Client threads
        for (int i = 0; i < 10; i++) {
            Client client = new Client(bank, accounts[i % accounts.length]);
            client.setName("Client " + (i + 1));
            client.start(); // Start client thread
        }
    }
}