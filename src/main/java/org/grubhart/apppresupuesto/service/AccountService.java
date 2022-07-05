package org.grubhart.apppresupuesto.service;

import org.grubhart.apppresupuesto.controller.AccountController;
import org.grubhart.apppresupuesto.domain.Account;

public interface AccountService {
    public Account transfer(String accountName, String accountTargetName, double amount);
}
