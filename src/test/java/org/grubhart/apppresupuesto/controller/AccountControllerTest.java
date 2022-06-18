package org.grubhart.apppresupuesto.controller;


import org.grubhart.apppresupuesto.controller.request.DepositRequest;
import org.grubhart.apppresupuesto.domain.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;


/*
Operaciones Expuestas por el API  (No CRUD  create read update delete)
*   Crear una cuenta
*  Consultar el saldo de la cuenta
*    Ejecutar un deposito a la cuenta
*    Ejecutar un retiro a la cuenta
*    Cerrar la cuenta
 */



@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = AccountController.class)
public class AccountControllerTest {


    public void setUp(ApplicationContext context){

    }


    /*
    Dado que no tengo una cuenta
    Invoco la creacion de una cuenta
    Debo tener una cuenta con Balance de 0
     */

    @Test
    public void CreaCuenta(@Autowired WebTestClient client){

        Account account = new Account();

        client.post()
                .uri("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(account))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.balance").isEqualTo(0.0);


    }

    /*
    Dado que tengo una cuenta con 20 soles
    cuando consulto el saldo
    me devuelve 20
     */
    @Test
    public void getEstadoCuenta(@Autowired WebTestClient client){

        client.get()
                .uri("/account/{nombreCuenta}","Ahorros")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Ahorros")
                .jsonPath("$.balance").isEqualTo(20.0);

    }


    /*
    Dado que tengo una cuenta llamada "ahorros" con balance de 20
    cuando deposito 5
    la cuenta tiene 25
     */
    @Test
    public void depositaCuenta(@Autowired WebTestClient client){

        DepositRequest request = new DepositRequest(5.0);

        client.post()
                .uri("/account/{name}/deposit","Ahorros")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Ahorros")
                .jsonPath("$.balance").isEqualTo(25.0);

    }

    /*
    Dado que tengo una cuenta llamada Ahorros con balance de 20
    cuando retiro 5
    la cuenta debe tener 15
     */
    @Test
    public void retiraCuenta(@Autowired WebTestClient client){

        DepositRequest request = new WithdrawRequest(5.0);

        client.post()
                .uri("/account/{name}/withdraw","Ahorros")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Ahorros")
                .jsonPath("$.balance").isEqualTo(15.0);

    }

    /*
    dado que tengo una cuenta llamada Ahorros
    cuando la elimino
    debe devolver la cuenta con estado cerrado = 2

    estados:
    0 cerrada
    1 abierta
    2 bloqueada
     */
    @Test
    public void cerrarCuenta(@Autowired WebTestClient client){

        client.post()
                .uri("/account/{name}/close","Ahorros")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Ahorros")
                .jsonPath("$.status").isEqualTo(0);

    }


}
