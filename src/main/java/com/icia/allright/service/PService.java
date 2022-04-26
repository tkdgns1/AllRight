package com.icia.allright.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.icia.allright.dao.PDAO;
import com.icia.allright.dto.FAVORITE;
import com.icia.allright.dto.PARKINGLOT;
import com.icia.allright.dto.QUESTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class PService {

    @Autowired
    private PDAO dao;

    ModelAndView mav = new ModelAndView();

    // 지도페이지로 이동
    public ModelAndView pMapForm() throws JsonProcessingException {

        List<PARKINGLOT> parkingList = dao.pList();

        mav.addObject("parking",parkingList);
        mav.setViewName("kmap");


        return mav;
    }

    // 주차장 상세정보 메소드
    public ModelAndView pView(String pCode) {
        PARKINGLOT parkinglot = dao.pView(pCode);

        if(parkinglot!=null){
            mav.addObject("parkinglot", parkinglot);
            mav.setViewName("pView");
        } else {
            mav.setViewName("redirect:/index");
        }

        return mav;
    }

    // 즐겨찾기 추가
    public ModelAndView pFavorite(FAVORITE favorite) {
        PARKINGLOT parkinglot = dao.pView(favorite.getFPcode());
        favorite.setFPname(parkinglot.getPName());
        favorite.setFPaddr(parkinglot.getPAddr());
        System.out.println("[1]service에서 favorite이 들어갔나요? : " + favorite);

        int result = dao.pFavorite(favorite);

        if(result > 0 ) {
            mav.setViewName("index");
        } else {
            mav.setViewName("redirect:/pView?pCode="+favorite.getFPcode());
        }

        return mav;
    }

    // 주차장 상세조회2
    public ModelAndView pView2(String pName) {
        System.out.println("[2] service : " + pName);
        PARKINGLOT parkinglot = dao.pView2(pName);
        System.out.println("[3] service : " + parkinglot);

        if(parkinglot!=null){
            mav.addObject("parkinglot", parkinglot);
            mav.setViewName("pView");
        } else {
            mav.setViewName("redirect:/index");
        }

        return mav;
    }
}
