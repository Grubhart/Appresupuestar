package org.grubhart.apppresupuesto.controller;

import org.grubhart.apppresupuesto.controller.request.DepositRequest;

public class WithdrawRequest extends DepositRequest {

    private double amount;

    public WithdrawRequest(double amount) {
        this.amount = amount;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public double getAmount(){
        return amount;
    }
}
