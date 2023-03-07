package com.example.eback.dao;

import com.example.eback.entity.Stock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockDAO extends JpaRepository<Stock,Integer> {
    Stock findByName(String name);
    Page<Stock> findAllBy(Pageable page);
    Stock findById(String id);
    boolean existsById(String sid);
}
