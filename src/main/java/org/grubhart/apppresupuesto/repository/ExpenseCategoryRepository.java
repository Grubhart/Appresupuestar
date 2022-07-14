package org.grubhart.apppresupuesto.repository;

import org.grubhart.apppresupuesto.domain.Account;
import org.grubhart.apppresupuesto.domain.ExpenseCategory;
import org.springframework.data.repository.CrudRepository;

public interface ExpenseCategoryRepository extends CrudRepository<ExpenseCategory, Long> {

    ExpenseCategory findByName(String name);

    ExpenseCategory save(Account account);
}
