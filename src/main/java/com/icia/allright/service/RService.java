package com.icia.allright.service;

import com.icia.allright.dao.MDAO;
import com.icia.allright.dao.PDAO;
import com.icia.allright.dao.RDAO;
import com.icia.allright.dto.PARKINGLOT;
import com.icia.allright.dto.RESERVE;
import com.icia.allright.dto.REVIEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class RService {

    @Autowired
    private RDAO dao;

    @Autowired
    private PDAO pdao;

    @Autowired
    private MDAO mdao;

    ModelAndView mav = new ModelAndView();

    // 결제 페이지로 이동
    public ModelAndView kakaopayForm(String pCode) {
        PARKINGLOT parkinglot = pdao.pView(pCode);

        if(parkinglot!=null){
            mav.addObject("parkinglot", parkinglot);
            mav.setViewName("kakaoPay");
        } else {
            mav.setViewName("redirect:/pView?pCode="+parkinglot.getPCode());
        }

        return mav;

    }

    // 예약하기
    public ModelAndView pReserve(String rPcode, String rId) {
        System.out.println("[2]Service 값이 넘어옴 ? : " + rPcode + rId);
        String pCode = rPcode;
        PARKINGLOT parkinglot = pdao.pView(pCode);

        System.out.println("[3]Service parkinglot 값이 저장됨 ? : " + parkinglot);
        RESERVE RESERVE = new RESERVE();
        RESERVE.setRId(rId);
        RESERVE.setRPcode(parkinglot.getPCode());
        RESERVE.setRPname(parkinglot.getPName());
        RESERVE.setRPaddr(parkinglot.getPAddr());
        RESERVE.setRPrice(parkinglot.getPPrice());

        System.out.println("[4]Service reserve에 값이 저장됨 ? : " + RESERVE);

        int result = dao.pReserve(RESERVE);
        int result2 = dao.minus(rPcode);

        System.out.println("[5]service : " +RESERVE.getRNo());
        int result3 = result + result2;
        if(result3 > 1) {
            mav.setViewName("rView");
            mav.addObject("reserve", RESERVE);
        } else {
            mav.setViewName("redirect:/pView?pCode="+rPcode);
        }
        return mav;
    }

    // 결제목록 조회
    public ModelAndView rList(String rId) {
        List<RESERVE> rList = dao.rList(rId);


        mav.addObject("rList",rList);
        mav.setViewName("rList");

        return mav;
    }

    // 결제 상세조회
    public ModelAndView rView(int rNo) {
        RESERVE RESERVE = dao.rView(rNo);

        if(RESERVE != null){
            mav.addObject("reserve", RESERVE);
            mav.setViewName("rView");
        }else{
            mav.setViewName("redirect:/index");
        }
        return mav;
    }

    // 리뷰 작성페이지로 이동
    public ModelAndView reviewForm() {
        List<PARKINGLOT> pList = mdao.plist();
        mav.setViewName("reWriteForm");
        mav.addObject("pList", pList);

        return mav;
    }

    // 후기 작성
    public ModelAndView reWrite(REVIEW review) throws IOException {

        // (1) 파일 불러오기
        MultipartFile reFile = review.getReFile();

        // (2) 파일이름 설정하기
        String originalFileName = reFile.getOriginalFilename();

        // 스프링 파일 업로드 할 때 문제점! + 파일 이름이 같을 경우!

        // (3) 난수 생성하기
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // (4) 난수와 파일이름 합치기 : d8nd01_이상해씨.jpg
        String reFileName = uuid + "_" + originalFileName;

        // (5) 파일 저장위치
        String savePath = "C:/Users/82106/Desktop/승재/공부/오픈소스 강의/Intellij/Project/AllRight/src/main/resources/static/profile/"+reFileName;
        /*String savePath = "C:/Users/kijin/OneDrive/바탕 화면/승재/IdeaProjects/AllRight/src/main/resources/static/profile/"+reFileName;*/
        // (6) 파일 선택여부
        if(!reFile.isEmpty()){
            review.setReFilename(reFileName);
            reFile.transferTo(new File(savePath));
        } else{
            review.setReFilename("default.png");
        }

        // Q. 어떤 작업? 가입(입력)
        // Q. 입력, 수정, 삭제 시 int result 사용!
        int result = dao.reWrite(review);

        if(result>0){
            // 성공
            mav.setViewName("index");
        } else {
            // 실패
            mav.setViewName("redirect:/rList?rId="+review.getReId());
        }

        return mav;
    }

    public ModelAndView rDelete(RESERVE reserve) {
        int result = dao.rDelete(reserve);

        if(result>0){
            mav.setViewName("redirect:/rList?rId="+reserve.getRId());
        }else{
            mav.setViewName("redirect:/rView?rNo="+reserve.getRNo());
        } return mav;

    }
}
