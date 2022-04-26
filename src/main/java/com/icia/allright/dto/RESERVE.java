package com.icia.allright.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;


@Data
@Alias("reserve")
public class RESERVE {
    private int rNo;
    private String rId;
    private String rPcode;
    private String rPname;
    private String rPaddr;
    private String rPrice;
    private Date rDate;

}
