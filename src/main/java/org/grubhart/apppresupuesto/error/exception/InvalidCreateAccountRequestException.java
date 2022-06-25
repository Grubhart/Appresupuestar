package org.grubhart.apppresupuesto.error.exception;

import org.grubhart.apppresupuesto.domain.Account;

public class InvalidCreateAccountRequestException extends RuntimeException{

    public InvalidCreateAccountRequestException(Account account) {
        super("Invalid Request " + account);
    }

}
