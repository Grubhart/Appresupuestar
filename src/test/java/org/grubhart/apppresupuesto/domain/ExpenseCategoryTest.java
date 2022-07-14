package org.grubhart.apppresupuesto.domain;

/*
Representacion de una categoria de gasto

 *   empieza con un Balance de 0
 *   Puedo Modificar la categoria: nombre, balance


 */

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseCategoryTest {


    private IDatabaseTester databaseTester;

    protected IDataSet getDataSet() throws Exception
    {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("C:\\Users\\edson\\IdeaProjects\\AppPresupuesto\\src\\test\\resources\\ExpenseCategoryTestDataSet.xml"));
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
    Dado que una categoria esta recien creada
    tiene un balance de 0
     */
    @Test
    public void testNuevaCategoriaBalance0(){

        ExpenseCategory category = new ExpenseCategory("" , 0);

        assertEquals(0, category.getBalance());

    }



    /*
    Dado que tengo uina categoria creada con el nombre de "Categoria 1" y balance 0
    cuando modifico el nombre y el balance a "Categoria de Gasto" y el balance a 50
    la categoria tiene dichos valores
     */

    @Test
    public void testModificaCategoria(){

        ExpenseCategory category = new ExpenseCategory("Categoria 1",0.0);

        category.setName("Categoria de Gasto");
        category.setBalance(50.00);

        assertEquals("Categoria de Gasto", category.getName());
        assertEquals(50.00, category.getBalance());
    }


}
