package com.icia.allright.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.icia.allright.dto.FAVORITE;
import com.icia.allright.dto.MEMBER;
import com.icia.allright.service.PService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class PController {

    @Autowired
    private PService psvc;

    ModelAndView mav = new ModelAndView();

    // pMap : 지도조회
    @RequestMapping(value = "/pMap", method = RequestMethod.GET)
    public ModelAndView pMapForm() throws JsonProcessingException {
        mav = psvc.pMapForm();
        return mav;
    }

    // pView : 주차장 상세조회
    @RequestMapping(value = "/pView", method = RequestMethod.GET)
    public ModelAndView pView(@RequestParam("pCode") String pCode){
        mav = psvc.pView(pCode);
        return mav;
    }

    // pFavorite : 즐겨찾기 추가
    @RequestMapping(value="pFavorite", method = RequestMethod.GET)
    public ModelAndView pFavorite(@ModelAttribute FAVORITE favorite){
        mav = psvc.pFavorite(favorite);
        return mav;
    }

    // pView2 : 주차장 상세 조회 2
    @RequestMapping(value = "/pView2", method = RequestMethod.GET)
    public ModelAndView pView2(@RequestParam("pName") String pName){
        mav = psvc.pView2(pName);
        return mav;
    }
}
