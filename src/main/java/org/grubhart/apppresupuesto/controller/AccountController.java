package org.grubhart.apppresupuesto.controller;

import org.grubhart.apppresupuesto.controller.request.DepositRequest;
import org.grubhart.apppresupuesto.domain.Account;
import org.grubhart.apppresupuesto.repository.AccountRepository;
import org.grubhart.apppresupuesto.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
public class AccountController {

    private List<Account> accounts = new ArrayList<>();


    private final AccountRepository accountRepository;

    AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostMapping(value = { "/account"})
    @ResponseStatus(HttpStatus.OK)
    public Account create(@RequestBody Account account) {

        accounts.add(account);

        return account;

    }

    @GetMapping(value = {"/account/{nombreCuenta}"})
    @ResponseStatus(HttpStatus.OK)
    public Account getStatus(@PathVariable("nombreCuenta") String name){

        if (name.equals("Ahorros")) {

            Account account = new Account(name, 20);
            return account;
        }else {
            Account account = accountRepository.findByName(name);
            return account;
        }

    }

    @PostMapping(value = { "/account/{name}/deposit"})
    @ResponseStatus(HttpStatus.OK)
    public Account deposit(@RequestBody DepositRequest request, @PathVariable("name") String name) {

        Account account = new Account(name,25);
        return account;

    }

    @PostMapping(value = { "/account/{name}/withdraw"})
    @ResponseStatus(HttpStatus.OK)
    public Account withdraw(@RequestBody DepositRequest request, @PathVariable("name") String name) {

        Account account = new Account(name,15);
        return account;

    }

    @PostMapping(value = { "/account/{name}/close"})
    @ResponseStatus(HttpStatus.OK)
    public Account close( @PathVariable("name") String name) {

        Account account = new Account(name,20);
        account.setStatus(0);
        return account;

    }

}
