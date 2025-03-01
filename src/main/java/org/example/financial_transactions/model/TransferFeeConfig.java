package org.example.financial_transactions.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "transfer.fee")
@Getter
@Setter
public class TransferFeeConfig {

    private Double percentage;

    private long floor;

    private long ceiling;

}
