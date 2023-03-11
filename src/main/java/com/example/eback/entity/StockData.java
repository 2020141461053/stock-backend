package com.example.eback.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "stock_data")
@ToString
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
/**
 * 股票信息需要结合Shrio来发布，这里的信息是前一天的保留信息，仅作为寻找Key加载用
 */
public class StockData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "stock_code")
    @CsvBindByName
//    @CsvBindByPosition(position = 0)
    private String sid;

   // private float last_value;//昨日数据?不太懂，等问一下再针对这个写逻辑，或者把这个放到stock类里，定时更新

    @CsvBindByName
//    @CsvBindByPosition(position = 2)
    private float value;

    @CsvBindByName
//    @CsvBindByPosition(position = 5)
    private  int turnover;

    @CsvBindByName
//    @CsvBindByPosition(position = 3)
    private float low;

    @CsvBindByName
//    @CsvBindByPosition(position = 4)
    private  float high;

    @CsvBindByName
//    @CsvBindByPosition(position = 1)
    @CsvDate("dd.MM.yyyy")
    private Date time;

    @CsvBindByName
//    @CsvBindByPosition(position = 6)
    private int volume;


    @CsvBindByName
    private float open;

    @CsvBindByName
    private  float close;

    @Transient
//    @CsvBindByPosition(position = 6)
    private float max_high;

    @Transient
//    @CsvBindByPosition(position = 7)
    private float min_low;







}
