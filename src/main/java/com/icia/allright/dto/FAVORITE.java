package com.icia.allright.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("favorite")
public class FAVORITE {
    private int fNo;
    private String fId;
    private String fPcode;
    private String fPname;
    private String fPaddr;
}
