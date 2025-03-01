package org.example.financial_transactions.service;

import org.example.financial_transactions.model.Customer;
import org.example.financial_transactions.model.dto.CustomerUpdateRequest;

public interface ICustomerService {

    void registerCustomer(Customer customer);

    void update(CustomerUpdateRequest customerUpdateRequest);

    Customer findById(Integer id);

    Boolean findDuplicateByNationalCodeAndId(String nationalCode, Integer id);
}
