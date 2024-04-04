package org.bankTest;

import org.bank.Bank;
import org.bank.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankSystemTest {
    private Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        // Create and add test accounts to the bank
        for (int i = 1; i <= 3; i++) {
            BankAccount account = new BankAccount("Acc-" + i, "Holder " + i, 1000);
            bank.addAccount(account);
        }
    }

    @Test
    public void testDeposit() {
        BankAccount account = bank.getAccount("Acc-1");
        double initialBalance = account.checkBalance();
        double depositAmount = 500;
        account.deposit(depositAmount);
        assertEquals(initialBalance + depositAmount, account.checkBalance());
    }

    @Test
    public void testWithdraw() {
        BankAccount account = bank.getAccount("Acc-2");
        double initialBalance = account.checkBalance();
        double withdrawAmount = 200;
        account.withdraw(withdrawAmount);
        assertEquals(initialBalance - withdrawAmount, account.checkBalance());
    }

    @Test
    public void testHandleLoanInstallment() {
        BankAccount account = bank.getAccount("Acc-3");
        double initialBalance = account.checkBalance();
        double installmentAmount = 300;
        account.handleLoanInstallment(installmentAmount);
        assertEquals(initialBalance - installmentAmount, account.checkBalance());
    }

    @Test
    public void testZeroDeposit() {
        Bank bank = new Bank();
        BankAccount account = new BankAccount("1", "Test Holder", 100);
        bank.addAccount(account);
        account.deposit(0);
        assertEquals(100, account.checkBalance());
    }

    @Test
    public void testLargeDeposit() {
        Bank bank = new Bank();
        BankAccount account = new BankAccount("2", "Test Holder", 100);
        bank.addAccount(account);
        account.deposit(1000000);
        assertEquals(1000100, account.checkBalance());
    }

    @Test
    public void testNegativeDeposit() {
        Bank bank = new Bank();
        BankAccount account = new BankAccount("3", "Test Holder", 100);
        bank.addAccount(account);
        account.deposit(-50);
        assertEquals(100, account.checkBalance());
    }

    @Test
    public void testWithdrawalWithInsufficientBalance() {
        Bank bank = new Bank();
        BankAccount account = new BankAccount("4", "Test Holder", 100);
        bank.addAccount(account);
        account.withdraw(200);
        assertEquals(100, account.checkBalance());
    }

    @Test
    public void testNonexistentAccountOperations() {
        Bank bank = new Bank();
        BankAccount account = new BankAccount("6", "Test Holder", 100);
        bank.addAccount(account);
        BankAccount nonExistentAccount = new BankAccount("7", "Nonexistent Holder", 0);
        account.deposit(50);
        nonExistentAccount.withdraw(50);
        assertEquals(150, account.checkBalance());
    }
}
