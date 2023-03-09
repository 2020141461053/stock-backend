package com.example.eback.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "stock")
@ToString
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Stock {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_code")
    @CsvBindByName
    private String id;

    @CsvBindByName
    private String name;

    @CsvBindByName
    @CsvDate("dd.MM.yyyy")
    private Date create_date;

    @CsvBindByName
    private float min_low;

    @CsvBindByName
    private float max_high;


    @Transient
    private float low;

    @Transient
    private  float high;

    @Transient
    private float value;

    @Transient
    private  int turnover;



}
