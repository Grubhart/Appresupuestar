package org.grubhart.apppresupuesto.domain;

/*
Representacion de una categoria de gasto

 *   empieza con un Balance de 0
 *   Puedo Modificar la categoria: nombre, balance


 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseCategoryTest {




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


    /*
    Dado que tengo una categoria de Nombre "Categoria_de_Gasto"  en estado abierto
    cuando la modifico a cerrado
    la categoria esta cerrada
     */
    @Test
    public void testCierraCategoria(){

        ExpenseCategory category = new ExpenseCategory("Categoria_de_Gasto",0.0);

        category.setStatus(ExpenseCategory.CLOSE);

        assertEquals(ExpenseCategory.CLOSE, category.getStatus());

    }


}
