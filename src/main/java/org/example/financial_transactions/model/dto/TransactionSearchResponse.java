package org.example.financial_transactions.model.dto;

import java.util.Date;

public record TransactionSearchResponse(Integer id,
                                        Double amount,
                                        Date creationDate,
                                        Long trackingCode) {

}
