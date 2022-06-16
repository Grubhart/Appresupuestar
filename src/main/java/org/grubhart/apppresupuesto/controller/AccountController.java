package org.grubhart.apppresupuesto.controller;

import org.grubhart.apppresupuesto.domain.Account;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountController {

    private List<Account> accounts = new ArrayList<>();

    @PostMapping(value = { "/account"})
    @ResponseStatus(HttpStatus.OK)
    public Account create(@RequestBody Account account) {

        accounts.add(account);

        return account;

    }

    @GetMapping(value = {"/account/{nombreCuenta}"})
    @ResponseStatus(HttpStatus.OK)
    public Account getStatus(@PathVariable("nombreCuenta") String name){
        Account account = new Account(name,20);
        return account;
    }

}
