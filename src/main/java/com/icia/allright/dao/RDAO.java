package com.icia.allright.dao;

import com.icia.allright.dto.RESERVE;
import com.icia.allright.dto.REVIEW;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RDAO {
    int pReserve(RESERVE RESERVE);

    List<RESERVE> rList(String rId);

    RESERVE rView(int rNo);

    int reWrite(REVIEW review);

    int minus(String rPcode);

    int rDelete(RESERVE reserve);
}
