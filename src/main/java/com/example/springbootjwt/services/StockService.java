package com.example.springbootjwt.services;

import com.example.springbootjwt.entities.Stock;
import com.example.springbootjwt.repo.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock saveStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public Stock updateStock(Long id, Stock stock) {
        Optional<Stock> existingStock = stockRepository.findById(id);
        if (existingStock.isPresent()) {
            Stock updatedStock = existingStock.get();
            updatedStock.setQuantity(stock.getQuantity());
            updatedStock.setItemId(stock.getItemId());
            return stockRepository.save(updatedStock);
        } else {
            // Handle case where stock is not found
            return null;
        }
    }

    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }

    public Optional<Stock> getStockById(Long id) {
        return stockRepository.findById(id);
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }
}
