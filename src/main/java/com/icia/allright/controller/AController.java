package com.icia.allright.controller;

import com.icia.allright.dto.ANOTICE;
import com.icia.allright.dto.PARKINGLOT;
import com.icia.allright.dto.QCOMMENT;
import com.icia.allright.service.AService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class AController {

    @Autowired
    private AService asvc;

    private ModelAndView mav = new ModelAndView();

    // aQlist : 전체 문의 내역 조회
    @RequestMapping(value = "aQlist", method = RequestMethod.GET)
    public ModelAndView aQlist(){
        System.out.println("[1]controller aQlist");
        mav = asvc.aQlist();
        return mav;
    }

    // aMlist : 회원 목록 조회
    @RequestMapping(value = "aMlist", method = RequestMethod.GET)
    public ModelAndView aMlist(){
        System.out.println(" ========= mList 메소드 ========= ");
        System.out.println("[1] controller");

        mav = asvc.aMlist();
        System.out.println("[4] controller : mav -> " + mav);

        return mav;
    }

    // writeForm : 공지 쓰기 페이지로 이동
    @RequestMapping(value = "/aNwriteForm", method = RequestMethod.GET)
    public String aNwriteForm() {
        return "nWriteForm";
    }

    //nWrite : 공지 작성
    @RequestMapping(value="/nWrite", method = RequestMethod.POST)
    public ModelAndView nWrite(@ModelAttribute ANOTICE anotice) throws IOException {
        mav = asvc.nWrite(anotice);
        return mav;
    }
    // nList : 공지목록보기
    @RequestMapping(value="/nList", method = RequestMethod.GET)
    public ModelAndView nList(){
        mav = asvc.nList();
        return mav;
    }

    // nView : 공지 상세보기
    @RequestMapping(value="/nView", method = RequestMethod.GET)
    public ModelAndView nView(@RequestParam("nNo")int nNo){
        mav = asvc.nView(nNo);
        return mav;
    }

    // nModiForm : 수정페이지로 이동
    @RequestMapping(value="nModiForm", method = RequestMethod.GET)
    public ModelAndView nModiForm(@RequestParam("nNo") int nNo){
        mav = asvc.nModiForm(nNo);
        return mav;
    }

    // nModify : 공지 수정
    @RequestMapping(value="/nModify", method = RequestMethod.POST)
    public ModelAndView nModify(@ModelAttribute ANOTICE anotice) throws IOException {
        mav = asvc.nModify(anotice);
        return mav;
    }

    // nDelete : 공지 삭제
    @RequestMapping(value="/nDelete", method = RequestMethod.GET)
    public ModelAndView nDelete(@RequestParam("nNo")int nNo){
        mav = asvc.nDelete(nNo);
        return mav;
    }

    // aPaddForm : 주차장 추가입력 페이지로 이동
    @RequestMapping(value = "/aPaddForm", method = RequestMethod.GET)
    public String aPaddForm() {
        return "PaddForm";
    }

    // pAdd : 주차장 추가
    @RequestMapping(value = "/pAdd", method = RequestMethod.POST)
    public ModelAndView pAdd(@ModelAttribute PARKINGLOT parkinglot){
        mav = asvc.pAdd(parkinglot);
        return mav;
    }

    // C_list : 댓글 리스트 불러오기
    @RequestMapping(value = "C_list", method=RequestMethod.POST)
    public @ResponseBody List<QCOMMENT> cList(@RequestParam("CBNo") int CBNo){
        List<QCOMMENT> commentList = asvc.cList(CBNo);
        return commentList;
    }

    // C_write : 댓글 작성
    @RequestMapping(value = "C_write", method=RequestMethod.POST)
    public @ResponseBody List<QCOMMENT> cWrite(@ModelAttribute QCOMMENT comment){
        List<QCOMMENT> commentList = asvc.cWrite(comment);
        return commentList;
    }

    // C_delete : 댓글 삭제
    @RequestMapping(value = "C_delete", method=RequestMethod.GET)
    public @ResponseBody List<QCOMMENT> cDelete(@ModelAttribute QCOMMENT comment){
        System.out.println("[1]Controller 댓글삭제 comment: " + comment);
        List<QCOMMENT> commentList = asvc.cDelete(comment);
        return commentList;
    }

    // aPlist : 전체 주차장 조회
    @RequestMapping(value="/aPlist", method = RequestMethod.GET)
    public ModelAndView aPlist(){
        mav = asvc.aPlist();
        return mav;
    }

    // aPdata : 주차장 데이터 관리
    @RequestMapping(value="/aPdata", method = RequestMethod.GET)
    public ModelAndView aPdata(){
        mav = asvc.aPdata();
        return mav;
    }

    // rdDelete : 데이터 삭제
    @RequestMapping(value="/rdDelete", method = RequestMethod.GET)
    public ModelAndView rdDelete(@RequestParam("rNo")int rNo, @RequestParam("rPname")String rPname) throws IOException {
        mav = asvc.rdDelete(rNo, rPname);
        return mav;
    }

    // autodelete : 자동 데이터 삭제
    @RequestMapping(value="autodelete", method = RequestMethod.GET)
    public ModelAndView autodelete() throws IOException {
        mav = asvc.autodelete();
        return mav;
    }
}
