package com.icia.allright.controller;

import com.icia.allright.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.icia.allright.service.MService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class MController {

    @Autowired
    private MService msvc;

    @Autowired
    private HttpSession session;

    private ModelAndView mav = new ModelAndView();

    // 프로젝트 시작 시 실행되는 메인페이지
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index1() {
        return "index";
    }

    // mPage : 마이 페이지로 이동
    @RequestMapping(value = "/mPage", method = RequestMethod.GET)
    public String Mypage() {
        return "Mypage";
    }

    // JoinForm : 회원 가입 페이지로 이동
    @RequestMapping(value = "/JoinForm", method = RequestMethod.GET)
    public String JoinForm() {
        return "joinForm";
    }

    // A_idoverlap : 아이디 중복 검사
    @RequestMapping(value="/idOverlap", method = RequestMethod.POST)
    public @ResponseBody
    String idOverlap(@RequestParam("mId") String mId) {
        System.out.println("=================idOverlap==============");
        System.out.println("[1]controller : mId -> " + mId);
        String result  = msvc.idOverlap(mId);
        System.out.println("[5]controller : result -> " + result);

        return result;
    }

    // mJoin : 회원가입
    @RequestMapping(value="mJoin", method = RequestMethod.POST)
    public ModelAndView mJoin(@ModelAttribute MEMBER member) throws IOException {
        System.out.println(" ========= mJoin 메소드 ========= ");
        System.out.println("[1]controller : member -> " + member);

        mav = msvc.mJoin(member);
        System.out.println("[4]controller : mav -> " + mav);

        return mav;
    }

    // 휴대폰 인증
    @RequestMapping(value="/phoneCheck", method = RequestMethod.GET)
    @ResponseBody
    public String sendSMS(@RequestParam("phone") String userPhoneNumber){
        int randomNumber = (int)((Math.random()* (9999 - 1000 + 1)) + 1000);

        MService.certifiedPhoneNumber(userPhoneNumber,randomNumber);

        return Integer.toString(randomNumber);
    }

    // loginForm : 로그인 페이지로 이동
    @RequestMapping(value="loginForm", method = RequestMethod.GET)
    public String loginForm(){
        return "loginForm";
    }

    // mLogin : 로그인
    @RequestMapping(value="mLogin", method = RequestMethod.POST)
    public ModelAndView mLogin(@ModelAttribute MEMBER member) throws IOException {
        System.out.println(" ========= mLogin 메소드 ========= ");
        System.out.println("[1]controller : member -> " + member);

        mav = msvc.mLogin(member);
        System.out.println("[4]controller : mav -> " + mav);

        return mav;
    }

    // mLogout : 로그아웃
    @RequestMapping(value="mLogout", method = RequestMethod.GET)
    public String mLogout(){
        System.out.println(" ========= logout 메소드 ========= ");
        session.invalidate();   // session을 초기화 하는 작업
        return "index";
    }

    /*// 카카오결제페이지
    @RequestMapping(value = "/kakaoPay", method = RequestMethod.GET)
    public String kakaoPay() {
        return "kakaoPay";
    }*/

    // mView : 내정보조회
    @RequestMapping(value = "mView", method = RequestMethod.GET)
    public ModelAndView mView(@RequestParam("mId") String mId){
        System.out.println("[1]controller mid" + mId);
        mav = msvc.mView(mId);
        return mav;
    }

    // qForm : 문의페이지로 이동
    @RequestMapping(value = "/qForm", method = RequestMethod.GET)
    public ModelAndView qForm() {
        mav = msvc.qForm();
        return mav;
    }

    // qWrite : 문의하기
    @RequestMapping(value="qWrite", method = RequestMethod.POST)
    public ModelAndView qWrite(@ModelAttribute QUESTION question) throws IOException {
        System.out.println(" ========= qWrite 메소드 ========= ");
        System.out.println("[1]controller : question -> " + question);

        mav = msvc.qWrite(question);
        System.out.println("[4]controller : mav -> " + mav);

        return mav;
    }

    // mqList : 문의 목록 조회
    @RequestMapping(value = "/mqList", method = RequestMethod.GET)
    public ModelAndView mqList(@RequestParam("qId") String qId) {
        System.out.println(" ========= mqList controller 메소드 ========= ");
        mav = msvc.mqList(qId);
        System.out.println("controller : mav -> " + mav);
        return mav;
    }

    /// mqView : 내문의목록 상세보기
    @RequestMapping(value = "/mqView", method = RequestMethod.GET)
    public ModelAndView mqView(@RequestParam("qNo") int qNo){
        System.out.println("== mqView ==");
        mav = msvc.mqView(qNo);
        System.out.println("controller : mav -> " + mav);
        return mav;
    }

    // qModiForm : 문의수정 페이지로 이동
    @RequestMapping(value = "/qModiForm", method = RequestMethod.GET)
    public ModelAndView qModiForm(@RequestParam("qNo") int qNo){
        mav = msvc.qModiForm(qNo);
        return mav;
    }

    // qModify : 문의 수정
    @RequestMapping(value = "/qModify", method = RequestMethod.POST)
    public ModelAndView qModify(@ModelAttribute QUESTION question) throws IOException {
        mav = msvc.qModify(question);
        return mav;
    }

    // qDelete: 문의 삭제
    @RequestMapping(value = "/qDelete", method = RequestMethod.GET)
    public ModelAndView qDelete(@ModelAttribute QUESTION question){
        mav = msvc.qDelete(question);
        return mav;
    }


    // mModiForm : 회원수정 페이지로 이동
    @RequestMapping(value = "/mModiForm", method = RequestMethod.GET)
    public ModelAndView mModiForm(@RequestParam("mId") String mId){
        mav = msvc.mModiForm(mId);
        return mav;
    }

    // mModify : 회원 수정
    @RequestMapping(value = "/mModify", method = RequestMethod.POST)
    public ModelAndView mModify(@ModelAttribute MEMBER member) throws IOException {
        mav = msvc.mModify(member);
        return mav;
    }

    // mDelete : 회원 삭제
    @RequestMapping(value = "/mDelete", method = RequestMethod.GET)
    public ModelAndView mDelete(@RequestParam("mId") String mId){
        mav = msvc.mDelete(mId);
        return mav;
    }

    // mfList : 즐겨찾기 목록
    @RequestMapping(value = "/mfList", method = RequestMethod.GET)
    public ModelAndView mfList(@RequestParam("fId") String fId){
        mav = msvc.mfList(fId);
        return mav;
    }

    // mfDelete : 즐겨찾기 삭제
    @RequestMapping(value = "/mfDelete", method = RequestMethod.GET)
    public ModelAndView mfDelete(@ModelAttribute FAVORITE favorite){
        mav = msvc.mfDelete(favorite);
        return mav;
    }

    // revList : 내 후기 목록
    @RequestMapping(value = "/revList", method = RequestMethod.GET)
    public ModelAndView revList(@RequestParam("reId") String reId){
        mav = msvc.revList(reId);
        return mav;
    }

    // revView : 후기 상세보기
    @RequestMapping(value = "/revView", method = RequestMethod.GET)
    public ModelAndView revView(@RequestParam("reNo") int reNo){
        mav = msvc.revView(reNo);
        return mav;
    }

    // reModiForm : 후기수정 페이지로 이동
    @RequestMapping(value = "/reModiForm", method = RequestMethod.GET)
    public ModelAndView reModiForm(@RequestParam("reNo") int reNo){
        mav = msvc.reModiForm(reNo);
        return mav;
    }

    // reModify : 후기 수정
    @RequestMapping(value = "/reModify", method = RequestMethod.POST)
    public ModelAndView reModify(@ModelAttribute REVIEW review) throws IOException {
        mav = msvc.reModify(review);
        return mav;
    }

    //reDelete : 후기 삭제
    @RequestMapping(value = "/reDelete", method = RequestMethod.GET)
    public ModelAndView reDelete(@ModelAttribute REVIEW review){
        mav = msvc.reDelete(review);
        return mav;
    }

    // eList : 만료데이터 보기
    @RequestMapping(value = "/eList", method = RequestMethod.GET)
    public ModelAndView eList(@RequestParam("eId") String eId){
        mav = msvc.eList(eId);
        return mav;
    }

    // eView : 만료데이터 상세조회
    @RequestMapping(value = "/eView", method = RequestMethod.GET)
    public ModelAndView eView(@RequestParam("eNo") int eNo){
        mav = msvc.eView(eNo);
        return mav;
    }
}