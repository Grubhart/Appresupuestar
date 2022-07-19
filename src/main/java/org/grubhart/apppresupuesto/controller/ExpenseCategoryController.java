package org.grubhart.apppresupuesto.controller;


import org.grubhart.apppresupuesto.domain.ExpenseCategory;
import org.grubhart.apppresupuesto.repository.ExpenseCategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExpenseCategoryController {


    private final ExpenseCategoryRepository categoryRepository;

    public ExpenseCategoryController(ExpenseCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping(value = { "/expensecategory"})
    @ResponseStatus(HttpStatus.OK)
    public ExpenseCategory create(@RequestBody ExpenseCategory category){

        ExpenseCategory  savedCategory =  categoryRepository.save(category);

        return savedCategory;

    }


    @GetMapping(value = {"/expensecategory/{nombreCategory}"})
    @ResponseStatus(HttpStatus.OK)
    public ExpenseCategory getStatus(@PathVariable("nombreCategory") String name){

        ExpenseCategory category = categoryRepository.findByName(name);
        return category;

    }

    @GetMapping(value = {"/expensecategory"})
    @ResponseStatus(HttpStatus.OK)
    public Iterable<ExpenseCategory> getCategories(){

        Iterable<ExpenseCategory> categories = categoryRepository.findAll();
        return categories;

    }


    @PostMapping(value = { "/expensecategory/{nombreCategory}"})
    @ResponseStatus(HttpStatus.OK)
    public ExpenseCategory update(@RequestBody ExpenseCategory categoryValuesToUpdate, @PathVariable("nombreCategory") String name){

        ExpenseCategory storedCategory = categoryRepository.findByName(name);

        storedCategory.setName(categoryValuesToUpdate.getName());
        storedCategory.setBalance(categoryValuesToUpdate.getBalance());

        ExpenseCategory  updatedCategopry =  categoryRepository.save(storedCategory);

        return updatedCategopry;

    }

    @PostMapping(value = { "/expensecategory/{nombreCategory}/close"})
    @ResponseStatus(HttpStatus.OK)
    public ExpenseCategory close(@PathVariable("nombreCategory") String name){

        ExpenseCategory storedCategory = categoryRepository.findByName(name);
        storedCategory.setStatus(ExpenseCategory.CLOSE);
        ExpenseCategory  updatedCategopry =  categoryRepository.save(storedCategory);

        return updatedCategopry;

    }
}
