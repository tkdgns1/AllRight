package com.icia.allright.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("parkinglot")
public class PARKINGLOT {
    private String pCode;
    private String pName;
    private String pAddr;
    private float pLa;
    private float pLo;
    private int pTotal;
    private int pAvail;
    private String pPrice;
    private String pTime;
    private String addr1;
    private String addr2;
    private String addr3;
}
