package com.icia.allright.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

@Data
@Alias("expiration")
public class EXPIRATION {
    private int eNo;
    private String eId;
    private String ePcode;
    private String ePname;
    private String ePaddr;
    private String ePrice;
    private Date eDate;
}
