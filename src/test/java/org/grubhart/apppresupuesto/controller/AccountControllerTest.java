package org.grubhart.apppresupuesto.controller;


import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.grubhart.apppresupuesto.controller.request.DepositRequest;
import org.grubhart.apppresupuesto.domain.Account;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.FileInputStream;



/*
Operaciones Expuestas por el API  (No CRUD  create read update delete)
*   Crear una cuenta
*   Consultar el saldo de la cuenta
*   Ejecutar un deposito a la cuenta
*   Ejecutar un retiro a la cuenta
*   Cerrar la cuenta
*   Transferencia entre cuentas
 */




@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class AccountControllerTest {

    private IDatabaseTester databaseTester;

    protected IDataSet getDataSet() throws Exception
    {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("C:\\Users\\edson\\IdeaProjects\\AppPresupuesto\\src\\test\\resources\\AccountTest.xml"));
    }


    @BeforeEach
    protected void setUp() throws Exception
    {

        databaseTester = new JdbcDatabaseTester("com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:59239/appresupuestar", "root", "my-secret-pw");

        // initialize your dataset here
        IDataSet dataSet = getDataSet();
        // ...

        databaseTester.setDataSet( dataSet );
        // will call default setUpOperation
        databaseTester.onSetup();
    }

    @AfterEach
    protected void tearDown() throws Exception
    {
        // will call default tearDownOperation
        databaseTester.onTearDown();
    }

    /*
    Dado que no tengo una cuenta
    Invoco la creacion de una cuenta sin nombre
    Debe arrojar un status bad request
     */

    @Test
    public void CreaCuentaIncompleta(@Autowired WebTestClient client){

        Account account = new Account();

        client.post()
                .uri("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(account))
                .exchange()
                .expectStatus().isBadRequest();


    }

      /*
    Dado que no tengo una cuenta
    Invoco la creacion de una cuenta nombre: Cuenta 01 balance 0
    Debe devolverme la cuenta creada con status creado
     */

    @Test
    public void CreaCuenta(@Autowired WebTestClient client){

        Account account = new Account("Cuenta 01",0.00);


        client.post()
                .uri("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(account))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Cuenta 01")
                .jsonPath("$.balance").isEqualTo(0.00)
                .jsonPath("$.status").isEqualTo(1);


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


    /*
    Dado que tengo una cuenta Cuenta1 con 100 y Cuenta2 con 80
    cuando transfiero a de la cuenta Cuenta1 50 a Cuenta 2
    el servicio retorna correcto y el estado de Cuenta1
     */
    @Test
    public void TranferirCuentaOk(@Autowired WebTestClient client){

        AccountTransferRequest request = new AccountTransferRequest("Cuenta2",50.00);

        client.post()
                .uri("/account/{nameOrigin}/transfer","Cuenta1")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Cuenta1")
                .jsonPath("$.balance").isEqualTo(50);

    }

    /*
    Dado que tengo una cuenta Cuenta3 con 10 y Cuenta2 con 80
    cuando transfiero a de la cuenta Cuenta3 50 a Cuenta2
    el servicio retorna codigo error 500
     */
    @Test
    public void TranferirCuentaOrigenFondosInsuficientes(@Autowired WebTestClient client){

        AccountTransferRequest request = new AccountTransferRequest("Cuenta2",50.00);

        client.post()
                .uri("/account/{nameOrigin}/transfer","Cuenta3")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().is5xxServerError();

    }

    /*
    Dado que tengo una cuenta CuentaOrigenBloqueada con 100 y Cuenta2 con 80
    cuando transfiero a de la cuenta CuentaOrigenBloqueada 50 a Cuenta2
    el servicio retorna codigo error 500
     */
    @Test
    public void TranferirCuentaOrigenCerrada(@Autowired WebTestClient client){

        AccountTransferRequest request = new AccountTransferRequest("CuentaOrigenBloqueada",50.00);

        client.post()
                .uri("/account/{nameOrigin}/transfer","CuentaOrigenBloqueada")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().is5xxServerError();

    }

    /*
    Dado que tengo una cuenta Ahorros con 20 y CuentaDestinoBloqueada con 100
    cuando transfiero a de la cuenta Ahorros 10 a CuentaDestinoBloqueada
    el servicio retorna codigo error 500
     */
    @Test
    public void TranferirCuentaDestinoCerrada(@Autowired WebTestClient client){

        AccountTransferRequest request = new AccountTransferRequest("Ahorros",10.00);

        client.post()
                .uri("/account/{nameOrigin}/transfer","CuentaDestinoBloqueada")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().is5xxServerError();

    }

    /*
   Dado que tengo una cuenta CuentaOrigenBloqueada con 100 y CuentaDestinoBloqueada con 100
   cuando transfiero a de la cuenta Ahorros 10 a CuentaDestinoBloqueada
   el servicio retorna codigo error 500
    */
    @Test
    public void TranferirCuentaOrigenDestinoCerrada(@Autowired WebTestClient client){

        AccountTransferRequest request = new AccountTransferRequest("CuentaOrigenBloqueada",10.00);

        client.post()
                .uri("/account/{nameOrigin}/transfer","CuentaDestinoBloqueada")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().is5xxServerError();

    }


}
