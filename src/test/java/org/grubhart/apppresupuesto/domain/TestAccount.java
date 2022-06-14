package org.grubhart.apppresupuesto.domain;

/*
1 crear una representacion de una cuenta

 *   empieza con un Balance de 0
 *   Puedo depositar dinero y aumenta el balance
 *   puedo retirar dinero y reduce el balance = balance inicial - monto retirado
 *   no tiene limite maximo
        -> depositos consecutivos no pueden dejarlo por encima del valor maximo
 *   no puede tener un balance menor de 0
            * no puede iniciar en negativo
            * los retiros no pueden dejarla en un balance negativo


            Z ero
            O ne
            M any
            B ehaviour
            I nterface
            E rror
            S imple

 */

import org.grubhart.apppresupuesto.exception.InvalidAmountException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAccount {

    /*
    ZERO
    dado que tengo una cuenta recien creada
    cuando consulto el balance de la cuenta
    obtengo 0
     */
    @Test
    public void testCuentaIniciaBalance0(){
        Account account = new Account();

        double  balance = account.getBalance();

        assertEquals(0.0, balance);
    }

    /*
    * Dado que tengo una cuenta recien creada
    * cuando deposito 10
    * el balance es 10
    * */
    @Test
    public void testPrimerDeposito(){

        Account account = new Account();

        account.deposit(10);

        double balance = account.getBalance();
        assertEquals(10.0, balance);
    }


    /*
    *  Dado que tengo una cuenta con 20 de saldo
    *  y retiro 14
    *  el saldo debe ser 6
     */

    @Test
    public void testRetiraDinero() throws InvalidAmountException {
        Account account = new Account(20.0);


        account.withdraw(14.0);

        assertEquals(6.0, account.getBalance());
    }


    /*
    Dado que tengo una cuenta que inicia en balance negativo
    la aplicacion arroja una Excepcion
     */

    @Test
    public void testIniciaBalanceNegativo(){

        assertThrows(InvalidAmountException.class, () -> {
            new Account(-10.0);
        });

    }


    /*
    *  Dado que tengo una cuenta con un balance inicial de 15
    *   y retiro 100
    *   la operacion debe arrojar un error
    */

    @Test
    public void testRetirayDejaBalanceNegativo(){

        Account account = new Account(15);

        assertThrows(InvalidAmountException.class, () ->{
            account.withdraw(100);
        });

    }

    /*
    * Dado que tengo una cuenta con balance inicial de 20
    * y ejecuto 3 retiros de 7
    * debe arrojar un error
    *
    * estamos testeando que se puede ejecutar multiples retiros antes de lanzar la excepcion
     */
    @Test
    public void testMultiplesRetiros(){

        Account account = new Account(20);

        try {
            account.withdraw(7);
            account.withdraw(5);
            account.withdraw(17);
            fail();
        }catch (InvalidAmountException e){
            assertEquals(8.0, account.getBalance());
        }


    }

    /*
        Dado que tengo una cuenta que inicia en 100 000 000
        cuando hago un deposito que lo deja por encima del valor maximo soportado
        debe arrojar error
     */
    @Test
    public void testDepositaPorEncimaDeLimite(){

        Account account = new Account(100000000.00);

        // spike cuanto seria lo que tengo que sumar

        double valorASumar = Double.MAX_VALUE - account.getBalance();

        assertThrows(InvalidAmountException.class, () ->{
            account.deposit(valorASumar+1);
        });

    }

}
