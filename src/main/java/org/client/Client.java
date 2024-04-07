package org.client;

import org.bank.Bank;
import org.bank.BankAccount;

import java.util.concurrent.ThreadLocalRandom;

public class Client extends Thread {
    private Bank bank;
    private BankAccount account;
    private String clientId;
    private String clientType; // Example: VIP, Regular
    private String clientAddress;
    private String clientPhone;

    /** Constructor to initialize client with bank, account, and additional attributes */
    public Client(Bank bank, BankAccount account, String clientId, String clientType, String clientAddress, String clientPhone) {
        this.bank = bank;
        this.account = account;
        this.clientId = clientId;
        this.clientType = clientType;
        this.clientAddress = clientAddress;
        this.clientPhone = clientPhone;
    }

    /** Run method to perform client transactions */
    @Override
    public void run() {
        System.out.println("[" + Thread.currentThread().getName() + "] started.");

        // Perform random transactions
        for (int i = 0; i < 10; i++) {
            int operation = ThreadLocalRandom.current().nextInt(5);
            double amount = ThreadLocalRandom.current().nextDouble(100, 1000);

            switch (operation) {
                case 0:
                    account.deposit(amount); // Deposit money into the account
                    break;
                case 1:
                    account.withdraw(amount); // Withdraw money from the account
                    break;
                case 2:
                    double balance = account.checkBalance(); // Check the balance of the account
                    System.out.println("[" + Thread.currentThread().getName() + "] Balance of account " + account.getAccountNumber() + ": " + balance);
                    break;
            }

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("[" + Thread.currentThread().getName() + "] finished.");
    }
}
