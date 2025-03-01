package org.example.financial_transactions.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.financial_transactions.dao.HistoryRepository;
import org.example.financial_transactions.model.History;
import org.example.financial_transactions.service.IHistoryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements IHistoryService {

    private final HistoryRepository repository;

    @Override
    public void save(History history) {
        repository.save(history);
    }
}
