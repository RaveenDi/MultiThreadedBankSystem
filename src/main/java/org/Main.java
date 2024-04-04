package org;

import org.bank.Bank;
import org.bank.BankAccount;
import org.client.Client;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        // Create accounts and add them to the bank
        for (int i = 1; i <= 10; i++) {
            BankAccount account = new BankAccount(Integer.toString(i), "Holder " + i, 100);
            bank.addAccount(account);
        }

        // Create and start client threads
        for (int i = 1; i <= 10; i++) {
            BankAccount account = bank.getAccount(Integer.toString(i));
            Thread clientThread = new Thread(new Client(bank, account), "Client " + i);
            clientThread.start();
        }

        // Simulate handling of loan installment by the bank
        Thread loanHandlerThread = new Thread(() -> {
            bank.handleLoanInstallment(500); // Deduct 500 from each selected account for loan installment
            try {
                Thread.sleep(5000); // Simulate monthly intervals
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "LoanHandlerThread");
        loanHandlerThread.start();
    }
}