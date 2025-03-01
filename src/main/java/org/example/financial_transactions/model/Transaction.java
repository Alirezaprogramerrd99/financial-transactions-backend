package org.example.financial_transactions.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.financial_transactions.model.enumutation.TransactionStatus;
import org.example.financial_transactions.model.enumutation.TransactionType;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "transaction_type")
    @Enumerated(value = EnumType.ORDINAL)
    private TransactionType transactionType;

    @CreationTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "tracking_code")
    private Long trackingCode;

    @Column(name ="transaction_status")
    @Enumerated(value = EnumType.ORDINAL)
    private TransactionStatus transactionStatus;
}
