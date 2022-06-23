package org.grubhart.apppresupuesto.repository;

import org.grubhart.apppresupuesto.domain.Account;
import org.springframework.data.repository.CrudRepository;



public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByName(String name);
}
