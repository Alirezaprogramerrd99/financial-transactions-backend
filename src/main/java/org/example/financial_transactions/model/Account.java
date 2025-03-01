package org.example.financial_transactions.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.financial_transactions.model.enumutation.AccountType;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {

    @NotBlank
    @Column(name = "account_number", nullable = false, length = 14)
    private String accountNumber;

    @CreationTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @NotNull
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @NotNull
    @Column(name = "balance", nullable = false)
    private Double balance;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account")
    private Customer customer;

    @OneToMany(mappedBy = "account")  // each account can have many histories...
    private List<History> histories = new ArrayList<>();

    @Override
    public String toString() {
        return "Account{" +
               "balance=" + balance +
               ", accountType=" + accountType +
               ", creationDate=" + creationDate +
               ", accountNumber='" + accountNumber + '\'' +
               '}';
    }
}
