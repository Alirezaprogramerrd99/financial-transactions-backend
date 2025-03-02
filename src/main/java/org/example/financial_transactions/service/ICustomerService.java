package org.example.financial_transactions.service;

import org.example.financial_transactions.model.Customer;
import org.example.financial_transactions.model.dto.CustomerUpdateRequest;

import java.util.Optional;

public interface ICustomerService {

    void registerCustomer(Customer customer);

    public Optional<Customer> getCustomerByNationalCode(String nationalCode);

    void update(CustomerUpdateRequest customerUpdateRequest);

    Customer findById(Integer id);

    Boolean findDuplicateByNationalCodeAndId(String nationalCode, Integer id);
}
