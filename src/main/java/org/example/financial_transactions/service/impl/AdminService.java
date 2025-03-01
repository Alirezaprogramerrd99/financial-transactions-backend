package org.example.financial_transactions.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.financial_transactions.dao.AdminRepository;
import org.example.financial_transactions.model.Account;
import org.example.financial_transactions.model.Admin;
import org.example.financial_transactions.model.enumutation.AccountType;
import org.example.financial_transactions.model.enumutation.Role;
import org.example.financial_transactions.service.IAccountService;
import org.example.financial_transactions.service.IAdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService implements IAdminService {
    private final AdminRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final IAccountService accountService;

    @PostConstruct
    public void init() {
        registerAdmin();
        registerBankAccount();
    }

    public void registerAdmin() {
        Optional<Admin> byNationalCode = repository.findByNationalCode("1111111111");
        if (byNationalCode.isEmpty()) {
            Admin admin = new Admin();
            admin.setNationalCode("1111111111");
            admin.setPassword(passwordEncoder.encode("22222222"));
            admin.setName("bank employee");
            admin.setRole(Role.ROLE_EMPLOYEE);
            admin.setAddress("tehran,golgasht ");
            admin.setPhoneNumber("09919855454");
            admin.setEstablishmentDate(LocalDate.now());
            admin.setPostalCode("7745874787");
            repository.save(admin);
        }
    }

    public void registerBankAccount() {
        if (!accountService.existsByAccountNumber("11111111111111")) {
            Account account = new Account("11111111111111", new Date(), AccountType.ACTIVE, 0D, null, null);
            accountService.pureSave(account);
        }
    }
}
