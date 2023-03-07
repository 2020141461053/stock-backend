package com.example.eback.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private String sid;

   // private float last_value;//昨日数据?不太懂，等问一下再针对这个写逻辑，或者把这个放到stock类里，定时更新

    private float value;

    private  int turnover;

    private float low;

    private  float high;

    private Date time;

    @Transient
    private float max_high;

    @Transient
    private float min_low;







}
