package com.icia.allright.dao;

import com.icia.allright.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ADAO {
    int pAdd(PARKINGLOT parkinglot);

    int nWrite(ANOTICE anotice);

    List<ANOTICE> nList();

    ANOTICE nView(int nNo);

    int nModify(ANOTICE anotice);

    int nDelete(int nNo);

    List<QUESTION> aQlist();

    List<QCOMMENT> cList(int cbNo);

    int cWrite(QCOMMENT comment);

    int cDelete(QCOMMENT comment);

    List<PARKINGLOT> aPlist();

    List<RESERVE> aPdata();

    int rdDelete(int rNo);

    int plus(String rPname);

    List<MEMBER> aMlist();

    int rmove(EXPIRATION expiration);
}
