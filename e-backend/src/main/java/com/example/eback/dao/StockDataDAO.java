package com.example.eback.dao;


import com.example.eback.entity.StockData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockDataDAO extends JpaRepository<StockData,Integer> {
    List<StockData> findBySid(String sid);
    Page<StockData> findAllBy(Pageable page);
    Page<StockData> findById(int id, Pageable page);

}
