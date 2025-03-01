package org.example.financial_transactions.dao;

import org.example.financial_transactions.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByNationalCode(String nationalCode);
}
