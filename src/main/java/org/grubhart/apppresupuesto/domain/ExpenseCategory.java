package org.grubhart.apppresupuesto.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String name;
    private double balance;

    public ExpenseCategory() {
    }

    public ExpenseCategory(String name, double balance) {

        this.name = name;
        this.balance = balance;

    }

    public double getBalance() {
        return balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(double balance) {
        this.balance=balance;
    }

    public String getName() {
        return name;
    }
}
