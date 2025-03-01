package org.example.financial_transactions.dao;

import org.example.financial_transactions.model.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Integer> {
}
