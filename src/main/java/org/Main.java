package org;

import org.bank.Bank;
import org.bank.BankAccount;
import org.client.Client;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        // Create VIP and Regular thread groups
        ThreadGroup vipThreadGroup = new ThreadGroup("VIP Clients");
        ThreadGroup regularThreadGroup = new ThreadGroup("Regular Clients");

        // Create accounts and add them to the bank
        for (int i = 1; i <= 10; i++) {
            BankAccount account = new BankAccount(Integer.toString(i), "Holder " + i, 100, "Savings", "Main Branch");
            bank.addAccount(account);
        }

        // Create and start client threads
        for (int i = 1; i <= 10; i++) {
            BankAccount account = bank.getAccount(Integer.toString(i));
            String clientId = "ID" + i;
            String clientType = isVIPClient(i) ? "VIP" : "Regular";
            String clientAddress = "Address" + i;
            String clientPhone = "+123456789" + i;

            Thread clientThread;
            if (isVIPClient(i)) {
                clientThread = new Thread(vipThreadGroup, new Client(bank, account, clientId, clientType, clientAddress, clientPhone), "VIP_Client_" + i);
                clientThread.setPriority(Thread.MAX_PRIORITY); // Set priority to maximum for VIP clients
            } else {
                clientThread = new Thread(regularThreadGroup, new Client(bank, account, clientId, clientType, clientAddress, clientPhone), "Regular_Client_" + i);
                clientThread.setPriority(Thread.MIN_PRIORITY); // Set priority to minimum for regular clients
            }
            clientThread.start();
        }


        // Simulate handling of loan installment by the bank
        Thread loanHandlerThread = new Thread(() -> {
            try {
                bank.handleLoanInstallment(500); // Deduct 500 from each selected account for loan installment
                Thread.sleep(5000); // Simulate monthly intervals
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "LoanHandlerThread");
        loanHandlerThread.start();

        // Display information about thread groups
        displayThreadGroupInfo(vipThreadGroup);
        displayThreadGroupInfo(regularThreadGroup);
    }

    // Check if the client is a VIP client
    private static boolean isVIPClient(int clientId) {
        // Sample logic to determine VIP clients
        return clientId % 2 == 0;
    }

    // Display information about a thread group
    private static void displayThreadGroupInfo(ThreadGroup threadGroup) {
        System.out.println("Thread Group Name: " + threadGroup.getName());
        System.out.println("Active Threads Count: " + threadGroup.activeCount());
        System.out.println("List of Active Threads:");
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        for (Thread thread : threads) {
            System.out.println("- " + thread.getName() + " Priority: " + thread.getPriority());
        }
        System.out.println();
    }
}