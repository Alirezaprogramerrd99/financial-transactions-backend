package org.example.financial_transactions.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.financial_transactions.model.enumutation.CustomerType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customers")
@ToString
@AllArgsConstructor
public class Customer extends User {

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "customer_type", nullable = false)
    private CustomerType customerType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "customer")
    private List<History> histories = new ArrayList<>();


    public Customer(String name, String nationalCode, LocalDate establishmentDate, String phoneNumber, String address, String postalCode, CustomerType customerType, Account account) {
        super(name, nationalCode, establishmentDate, phoneNumber, address, postalCode);
        this.customerType = customerType;
        this.account = account;
    }

    public Customer(String name, String nationalCode, LocalDate establishmentDate, String phoneNumber, String address, String postalCode) {
        super(name, nationalCode, establishmentDate, phoneNumber, address, postalCode);
    }
}
