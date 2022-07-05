package org.grubhart.apppresupuesto.controller;

import org.grubhart.apppresupuesto.controller.request.DepositRequest;
import org.grubhart.apppresupuesto.domain.Account;
import org.grubhart.apppresupuesto.error.exception.InvalidCreateAccountRequestException;
import org.grubhart.apppresupuesto.repository.AccountRepository;
import org.grubhart.apppresupuesto.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final AccountService accountService;


    private final AccountRepository accountRepository;

    AccountController(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @PostMapping(value = { "/account"})
    @ResponseStatus(HttpStatus.OK)
    public Account create(@RequestBody Account account) {


        Account savedAccount = null;

        boolean validRequest = true;

        validRequest &= account.getName()!=null;

        if(validRequest) {
            savedAccount = accountRepository.save(account);
        }else{
            throw new InvalidCreateAccountRequestException(account);
        }
        return savedAccount;

    }

    @GetMapping(value = {"/account/{nombreCuenta}"})
    @ResponseStatus(HttpStatus.OK)
    public Account getStatus(@PathVariable("nombreCuenta") String name){

        Account account = accountRepository.findByName(name);
        return account;

    }

    @PostMapping(value = { "/account/{name}/deposit"})
    @ResponseStatus(HttpStatus.OK)
    public Account deposit(@RequestBody DepositRequest request, @PathVariable("name") String name) {

        Account accountToDeposit = accountRepository.findByName(name);

        accountToDeposit.deposit(request.getAmount());

        Account savedAccount = accountRepository.save(accountToDeposit);



        return savedAccount;

    }

    @PostMapping(value = { "/account/{name}/withdraw"})
    @ResponseStatus(HttpStatus.OK)
    public Account withdraw(@RequestBody DepositRequest request, @PathVariable("name") String name) {

        Account account = accountRepository.findByName(name);
        account.withdraw(request.getAmount());
        accountRepository.save(account);
        return account;

    }

    @PostMapping(value = { "/account/{name}/close"})
    @ResponseStatus(HttpStatus.OK)
    public Account close( @PathVariable("name") String name) {

        Account account = accountRepository.findByName(name);
        account.setStatus(0);
        return accountRepository.save(account);

    }


    @PostMapping(value = { "/account/{accountName}/transfer"})
    @ResponseStatus(HttpStatus.OK)
    public Account transfer( @PathVariable("accountName") String accountName, @RequestBody AccountTransferRequest transferRequest)  {


        String accountTargetName = transferRequest.getAccountTargetName();
        double amount = transferRequest.getAmount();

        Account updatedAccount = accountService.transfer(accountName, accountTargetName, amount);

        return updatedAccount;


    }

}
