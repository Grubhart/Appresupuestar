package org.grubhart.apppresupuesto.controller;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.grubhart.apppresupuesto.domain.ExpenseCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.FileInputStream;

/*
Operaciones Expuestas por el API  (No CRUD  create read update delete)
        *  Crear una categoria
        *  Consultar una cuenta por nombre
        *  Listar Categorias
        *   Modificar Categorias
        ->   Cerrar Categorias
*/


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class CategoryControllerTest {

    private IDatabaseTester databaseTester;

    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("C:\\Users\\edson\\IdeaProjects\\AppPresupuesto\\src\\test\\resources\\ExpenseCategoryTestDataSet.xml"));
    }


    @BeforeEach
    protected void setUp() throws Exception {

        databaseTester = new JdbcDatabaseTester("com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:59239/appresupuestar", "root", "my-secret-pw");

        // initialize your dataset here
        IDataSet dataSet = getDataSet();
        // ...

        databaseTester.setDataSet(dataSet);
        // will call default setUpOperation
        databaseTester.onSetup();
    }

    @AfterEach
    protected void tearDown() throws Exception {
        // will call default tearDownOperation
        databaseTester.onTearDown();
    }


    /*
    Dado que Creo una categoria llamada "Categoria de gasto" con balance 0
    el endpoint devuelve un status ok y la informacion de la categoria
     */
    @Test
    public void testCrearCategoria(@Autowired WebTestClient client) {

        ExpenseCategory category = new ExpenseCategory("Categoria_de_Gasto", 0);

        client.post()
                .uri("/expensecategory")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(category))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Categoria_de_Gasto")
                .jsonPath("$.balance").isEqualTo(0.00);

    }

    /*
    Dado que tengo una categoria llamada "Categoria de gasto" con balance 30
    el endpoint devuelve un status ok y la informacion de la categoria
     */
    @Test
    public void testConsultaCategoria(@Autowired WebTestClient client) {


        client.get()
                .uri("/expensecategory/{nombreCategoria}", "Categoria_de_Gasto")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Categoria_de_Gasto")
                .jsonPath("$.balance").isEqualTo(30.00);

    }


    /*
    Dado que tengo 2 categorias llamada "Categoria_de_gasto" y "Categoria_de_gasto1" con balance 30
    el endpoint devuelve un status ok y la informacion de ambas categorias
     */
    @Test
    public void testConsultaCategorias(@Autowired WebTestClient client) {

        client.get()
                .uri("/expensecategory")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ExpenseCategory.class).hasSize(2);
    }

    /*
    Dado que tengo una 1 categoria llamada "Categoria_de_gasto" con balance 30
    cuando modifico el nombre a "categoria" y el balance a 50
    el endpoint devuelve estado correcto y la nueva informacion de la categoria
     */

    @Test
    public void testActualizaCategoria(@Autowired WebTestClient client) {

        ExpenseCategory category = new ExpenseCategory("Categoria", 50);

        client.post()
                .uri("/expensecategory/{nombreCategoria}", "Categoria_de_Gasto")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(category))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Categoria")
                .jsonPath("$.balance").isEqualTo(50.00);

    }

     /*
    Dado que tengo una 1 categoria llamada "Categoria_de_gasto" con balance 30 y estado Abierto
    cuando invoco el endpoint de cierre de categoria
    el endpoint devuelve estado correcto y la categoria con su nuevo estado
     */

    @Test
    public void testCierraCategoria(@Autowired WebTestClient client) {



        client.post()
                .uri("/expensecategory/{nombreCategoria}/close", "Categoria_de_Gasto")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Categoria_de_Gasto")
                .jsonPath("$.balance").isEqualTo(30.00)
                .jsonPath("$.status").isEqualTo(ExpenseCategory.CLOSE);
    }

}
