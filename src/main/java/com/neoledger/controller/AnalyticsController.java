package com.neoledger.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.neoledger.entity.Transaction;
import com.neoledger.repository.TransactionRepository;

@RestController
@RequestMapping("/api")
public class AnalyticsController {

    @Autowired
    private TransactionRepository repo;

    @GetMapping("/analytics")
    public Map<String, Double> getAnalytics() {

        List<Transaction> txns = repo.findAll();

        double income = txns.stream()
                .filter(t -> t.getAmount().doubleValue() > 0)
                .mapToDouble(t -> t.getAmount().doubleValue())
                .sum();

        double expense = txns.stream()
                .filter(t -> t.getAmount().doubleValue() < 0)
                .mapToDouble(t -> Math.abs(t.getAmount().doubleValue()))
                .sum();

        Map<String, Double> result = new HashMap<>();
        result.put("income", income);
        result.put("expense", expense);

        return result;
    }
}