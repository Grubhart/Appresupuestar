package org.grubhart.apppresupuesto.controller;


import org.grubhart.apppresupuesto.domain.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;


/*
Operaciones Expuestas por el API  (No CRUD  create read update delete)
->   Crear una cuenta
    Consultar el saldo de la cuenta
    Ejecutar un deposito a la cuenta
    Ejecutar un retiro a la cuenta
    Cerrar la cuenta
 */


@SpringBootTest
@AutoConfigureWebTestClient
//@ExtendWith(SpringExtension.class)
//@WebFluxTest(controllers = AccountController.class)
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
        client.post()
                .uri("/account")
                .contentType(MediaType.APPLICATION_JSON)
                //.body(BodyInserters.fromObject(employee))
                .exchange()
                .expectStatus().isOk();


    }

}
