package org.grubhart.apppresupuesto.service.impl;

import org.grubhart.apppresupuesto.controller.AccountController;
import org.grubhart.apppresupuesto.domain.Account;
import org.grubhart.apppresupuesto.repository.AccountRepository;
import org.grubhart.apppresupuesto.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }


    @Override
    public Account transfer(String accountName, String accountTargetName, double amount) {
        Account accountOrigen = repository.findByName(accountName);
        Account accountTarget = repository.findByName(accountTargetName);

        accountOrigen.withdraw(amount);
        accountTarget.deposit(amount);

        repository.save(accountTarget);
        Account updatedAccount = repository.save(accountOrigen);
        return updatedAccount;
    }

}
