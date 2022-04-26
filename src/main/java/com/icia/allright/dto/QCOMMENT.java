package com.icia.allright.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("qcomment")
public class QCOMMENT {
    private int qCno;
    private int qCbno;
    private String qCwriter;
    private String qCcontent;
}
