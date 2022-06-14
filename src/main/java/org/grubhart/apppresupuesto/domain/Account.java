package org.grubhart.apppresupuesto.domain;

import org.grubhart.apppresupuesto.exception.InvalidAmountException;

public class Account {

    private double balance = 0.0;

    public Account() {
    }

    public Account(double initialAmount) {
        if(initialAmount < 0.00000000000000){
            throw new InvalidAmountException();
        }

        this.balance = initialAmount;
    }


    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {

        if(amount >= Double.MAX_VALUE-getBalance()){
            throw new InvalidAmountException();
        }

        this.balance += amount;
    }

    public void withdraw(double amount) {

        if(amount > this.balance){
            throw new InvalidAmountException();
        }
        this.balance -=  amount;

    }
}
