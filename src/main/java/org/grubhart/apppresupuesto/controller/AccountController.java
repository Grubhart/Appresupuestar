package org.grubhart.apppresupuesto.controller;

import org.grubhart.apppresupuesto.domain.Account;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @PostMapping(value = { "/account",  })
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody Account account) {

    }

}
