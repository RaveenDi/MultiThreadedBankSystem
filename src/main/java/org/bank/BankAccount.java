package org.bank;

public class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    /** Constructor to initialize the bank account */
    public BankAccount(String accountNumber, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
    }

    /** Method to get the account number */
    public String getAccountNumber() {
        return accountNumber;
    }

    /** Method to deposit money into the account */
    public synchronized void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("[" + Thread.currentThread().getName() + "] Deposit of " + amount + " made to account " + accountNumber + ". New balance: " + balance);
            notifyAll(); // Notify waiting threads about the balance change
        } else {
            System.out.println("[" + Thread.currentThread().getName() + "] Error: Invalid deposit amount for account " + accountNumber + ".");
        }
    }

    /** Method to withdraw money from the account */
    public synchronized void withdraw(double amount) {
        if (amount > 0) {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("[" + Thread.currentThread().getName() + "] Withdrawal of " + amount + " made from account " + accountNumber + ". New balance: " + balance);
            } else {
                System.out.println("[" + Thread.currentThread().getName() + "] Error: Insufficient balance for withdrawal from account " + accountNumber + ".");
            }
            notifyAll(); // Notify waiting threads about the balance change
        } else {
            System.out.println("[" + Thread.currentThread().getName() + "] Error: Invalid withdrawal amount for account " + accountNumber + ".");
        }
    }

    /** Method to check the current balance of the account */
    public synchronized double checkBalance() {
        return balance;
    }

    /** Method to handle the deduction of loan installment from the account balance */
    public synchronized void handleLoanInstallment(double installmentAmount) {
        long startTime = System.currentTimeMillis();
        boolean timeoutOccurred = false;

        while (balance < installmentAmount && !timeoutOccurred) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - startTime;
            long remainingTime = 5000 - elapsedTime;

            if (remainingTime <= 0) {
                // Timeout reached, notify agent
                System.out.println(Thread.currentThread().getName() + ": Timeout reached while waiting for sufficient" +
                        " balance in account " + accountNumber +
                        ". Contact client.");
                timeoutOccurred = true;
            }

            System.out.println(Thread.currentThread().getName() + ": Waiting for sufficient balance in account " + accountNumber +
                    ". Remaining time: " + remainingTime + " milliseconds");

            try {
                // Wait until balance is sufficient or timeout occurs
                wait(remainingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!timeoutOccurred) {
            balance -= installmentAmount;
            System.out.println("[Bank] Loan installment of " + installmentAmount +
                    " deducted from account " + accountNumber + ". New balance: " + balance);
        }
    }
}
