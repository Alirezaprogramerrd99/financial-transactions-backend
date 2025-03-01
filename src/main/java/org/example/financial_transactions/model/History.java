package org.example.financial_transactions.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "history")
@ToString
public class History extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)  // (foreign key) to Account table
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)  // (foreign key) to customer table
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
