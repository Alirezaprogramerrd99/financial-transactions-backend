package org.example.financial_transactions.dao;

import org.example.financial_transactions.model.Account;
import org.example.financial_transactions.model.dto.CustomerSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query(value = "SELECT nextval('seq_account_number')", nativeQuery = true)
    Long generateAccountNumber();


    @Query("""
            select a.accountNumber from Account a left join a.customer c
             where c.nationalCode=:nationalCode
            """)
    String findAccountNumberByNationalCode(@Param("nationalCode") String nationalCode);

    @Query("""
            select a.balance from Account a where a.accountNumber =:accountNumber
            """)
    Double findBalanceByAccountNumber(@Param("accountNumber") String accountNumber);


    @Query("""
            select count(c)> 0 from Customer c where c.nationalCode =:nationalCode
            """)
    Boolean existsByNationalCode(@Param("nationalCode") String nationalCode);

    @Query("""
            select count (a) > 0 from Account a where a.accountNumber =:accountNumber
            """)
    Boolean existByAccountNumber(@Param("accountNumber") String accountNumber);

    @Query("""
              select new org.example.financial_transactions.model.dto.CustomerSummary(
                      c.name,c.nationalCode,c.establishmentDate,c.customerType,
                      c.phoneNumber,c.address,c.postalCode,a.accountType,
                      a.accountNumber)
              from Account a left join a.customer c where a.accountNumber=:accountNumber
            """)
    Optional<CustomerSummary> getByAccountNumber(@Param("accountNumber") String accountNumber);

    Optional<Account> findByAccountNumber(String accountNumber);
}
