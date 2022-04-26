package com.icia.allright.controller;

import com.icia.allright.dto.QUESTION;
import com.icia.allright.dto.RESERVE;
import com.icia.allright.dto.REVIEW;
import com.icia.allright.service.RService;
import org.apache.maven.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class RController {

    @Autowired
    private RService rsvc;

    @Autowired
    private HttpSession session;

    private ModelAndView mav = new ModelAndView();

    // kakaopayForm : 결제 페이지로 이동
    @RequestMapping(value = "/kakaopayForm", method = RequestMethod.GET)
    public ModelAndView kakaopayForm(@RequestParam("pCode") String pCode) {
        mav = rsvc.kakaopayForm(pCode);
        return mav;
    }

    // pReserve : 결제 정보 저장 메소드
    @RequestMapping(value = "/pReserve", method = RequestMethod.GET)
    public ModelAndView pReserve(@RequestParam("rPcode") String rPcode, @RequestParam("rId") String rId) {
        System.out.println("[1]Controller html에서 값이 넘어왔는가 ? : " + rPcode + rId);
        mav = rsvc.pReserve(rPcode, rId);
        return mav;
    }

    // rList : 결제 목록 조회
    @RequestMapping(value = "/rList", method = RequestMethod.GET)
    public ModelAndView rList(@RequestParam("rId") String rId) {
        System.out.println("==rList==");
        mav = rsvc.rList(rId);
        return mav;
    }

    // rView : 결제 상세 조회
    @RequestMapping(value = "/rView", method = RequestMethod.GET)
    public ModelAndView rView(@RequestParam("rNo") int rNo) {
        System.out.println("==rView==");
        mav = rsvc.rView(rNo);
        return mav;
    }

    // reviewForm : 후기 작성페이지로 이동
    @RequestMapping(value = "/reviewForm", method = RequestMethod.GET)
    public ModelAndView reviewForm() {
        mav = rsvc.reviewForm();
        return mav;
    }

    // reWrite : 후기 작성
    @RequestMapping(value = "reWrite", method = RequestMethod.POST)
    public ModelAndView reWrite(@ModelAttribute REVIEW review) throws IOException {
        mav = rsvc.reWrite(review);
        return mav;
    }

    // rDelete : 결제취소
    @RequestMapping(value = "/rDelete",method = RequestMethod.GET)
    public ModelAndView rDelete(@ModelAttribute RESERVE reserve){

        mav = rsvc.rDelete(reserve);

        return mav;
    }

}
