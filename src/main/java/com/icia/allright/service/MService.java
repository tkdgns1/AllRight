package com.icia.allright.service;

import com.icia.allright.dao.MDAO;
import com.icia.allright.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class MService {
    @Autowired
    private MDAO dao;

    @Autowired
    private PasswordEncoder pwEnc;

    @Autowired
    private HttpServletResponse response;

    private ModelAndView mav = new ModelAndView();

    @Autowired
    private HttpSession session;

    // 회원가입 메소드
    public ModelAndView mJoin(MEMBER member) throws IOException {
        System.out.println("[2]service : member -> " + member);

        member.setMPw(pwEnc.encode(member.getMPw()));

        // (1) 파일 불러오기
        MultipartFile mLicence = member.getMLicence();

        // (2) 파일이름 설정하기
        String originalFileName = mLicence.getOriginalFilename();

        // 스프링 파일 업로드 할 때 문제점! + 파일 이름이 같을 경우!

        // (3) 난수 생성하기
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // (4) 난수와 파일이름 합치기 : d8nd01_이상해씨.jpg
        String mLicenceName = uuid + "_" + originalFileName;

        // (5) 파일 저장위치
        /*String savePath = "C:/Users/82106/Desktop/승재/공부/오픈소스 강의/Intellij/Project/AllRight/src/main/resources/static/profile/"+mLicenceName;*/
        String savePath = "C:/Users/kijin/OneDrive/바탕 화면/승재/IdeaProjects/AllRight/src/main/resources/static/profile/"+mLicenceName;
        // (6) 파일 선택여부
        if(!mLicence.isEmpty()){
            member.setMLicenceName(mLicenceName);
            mLicence.transferTo(new File(savePath));
        } else{
            member.setMLicenceName("default.png");
        }


        // Q. 어떤 작업? 가입(입력)
        // Q. 입력, 수정, 삭제 시 int result 사용!
        int result = dao.mJoin(member);
        System.out.println("[3]service : result -> " + result);

        if(result>0){
            // 성공
            mav.setViewName("index");
        } else {
            // 실패
            mav.setViewName("joinForm");
        }

        return mav;
    }

    // 휴대폰 인증
    public static void certifiedPhoneNumber(String userPhoneNumber, int randomNumber) {
        String api_key= "NCSVWASZEWB9PF9K";
        String api_secret = "5H4NR3OWHSED41WZRLXPUV9ZGHZQRO0M";
        Message coolsms = new Message(api_key, api_secret);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", userPhoneNumber);
        params.put("from","01033121657");

        params.put("type", "SMS");
        params.put("text","[TEST] 인증번호는" + "["+randomNumber+"]" + "입니다.");
        params.put("app_version", "test app 1.2");

        try{
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
            e.printStackTrace();
        }
    }

    // 아이디 중복검사
    public String idOverlap(String mId) {
        System.out.println("[2]service : MId -> " + mId);

        String idCheck = dao.idOverlap(mId);

        String result = null;

        if(idCheck==null) {
            result = "OK";   // 중복x
        } else {
            result = "NO";   // 중복o
        }
        System.out.println("[4]service : result -> " + result);
        return result;
    }

    // 로그인 메소드
    public ModelAndView mLogin(MEMBER member) throws IOException {
        System.out.println("[2]service : member -> " + member);

        // 입력한 id와 pw가 일치할 경우
        // id가 존재하는지 존재하지 않는지.. 존재한다면 id를 가져온다!

        // db에서 받아온 암호화 된 pw
        MEMBER member1 = dao.mLogin(member);

        System.out.println("[2]service : mPw -> " + member.getMPw());
        System.out.println("[2]service : secuPw -> " + member1.getMPw());
        System.out.println("[2]service : boolean -> " + pwEnc.matches(member.getMPw(), member1.getMPw()));



        System.out.println("로그인 성공시 ======= \n " + member1);

        if(pwEnc.matches(member.getMPw(),member1.getMPw())){
            // 로그인 성공

            System.out.println("로그인 성공!");
            session.setAttribute("loginId", member.getMId());
            mav.setViewName("index");
        } else {
            // 로그인 실패
            System.out.println("로그인 실패!");
            response.setContentType("text/html; charset=UTF-8");

            PrintWriter out = response.getWriter();

            out.println("<script>alert('로그인실패!');</script>");
            mav.setViewName("loginForm");
        }
        return mav;
    }

    // 문의 페이지로 이동
    public ModelAndView qForm() {
        List<PARKINGLOT> pList = dao.plist();
        mav.setViewName("qWriteForm");
        mav.addObject("pList", pList);

        return mav;
    }

    // 문의 작성
    public ModelAndView qWrite(QUESTION question) throws IOException {
        System.out.println("[2]service : question -> " + question);

        // (1) 파일 불러오기
        MultipartFile qFile = question.getQFile();

        // (2) 파일이름 설정하기
        String originalFileName = qFile.getOriginalFilename();

        // 스프링 파일 업로드 할 때 문제점! + 파일 이름이 같을 경우!

        // (3) 난수 생성하기
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // (4) 난수와 파일이름 합치기 : d8nd01_이상해씨.jpg
        String qFileName = uuid + "_" + originalFileName;

        // (5) 파일 저장위치
        /*String savePath = "C:/Users/82106/Desktop/승재/공부/오픈소스 강의/Intellij/Project/AllRight/src/main/resources/static/profile/"+qFileName;*/
        String savePath = "C:/Users/kijin/OneDrive/바탕 화면/승재/IdeaProjects/AllRight/src/main/resources/static/profile/"+qFileName;
        // (6) 파일 선택여부
        if(!qFile.isEmpty()){
            question.setQFilename(qFileName);
            qFile.transferTo(new File(savePath));
        } else{
            question.setQFilename("default.png");
        }


        // Q. 어떤 작업? 가입(입력)
        // Q. 입력, 수정, 삭제 시 int result 사용!
        int result = dao.qWrite(question);
        System.out.println("[3]service : result -> " + result);

        if(result>0){
            // 성공
            mav.setViewName("index");
        } else {
            // 실패
            mav.setViewName("qWriteForm");
        }

        return mav;
    }

    // 내 문의 목록
    public ModelAndView mqList(String qId) {
        System.out.println("[2]Service");

        List<QUESTION> mqList = dao.mqList(qId);
        System.out.println("[3]service : mqList -> " + mqList);

        mav.setViewName("mqList");
        mav.addObject("mqList", mqList);

        return mav;
    }

    // 내 문의내역 상세 조회
    public ModelAndView mqView(int qNo) {
        System.out.println("[2]Service");

        QUESTION question = dao.mqView(qNo);
        System.out.println("[3]Service mqView ->");

        if(question!=null){
            mav.addObject("question", question);
            mav.setViewName("mqView");
        } else {
            mav.setViewName("redirect:/index");
        }

        return mav;
    }

    // 내정보보기
    public ModelAndView mView(String mId) {
        MEMBER member = dao.mView(mId);

        if (member != null) {
            // 검색한 회원의 정보가 존재할 때 (not null일 때)
            mav.addObject("member", member);
            mav.setViewName("mView");
        } else {
            // 검색한 회원의 정보가 존재하지 않을 때 -> 리스트로 돌아가기
            // html파일이 아닌 controller의 주소로 값을 보낼 때 redirect:/주소
            mav.setViewName("redirect:/index");
        }
        return mav;
    }

    // 회원정보 수정 페이지로 이동
    public ModelAndView mModiForm(String mId) {
        MEMBER member = dao.mView(mId);

        if(member!=null){
            mav.setViewName("mModify");
            mav.addObject("member",member);
        } else{
            mav.setViewName("redirect:/mList");
        }
        return mav;
    }

    // 회원 수정 메소드
    public ModelAndView mModify(MEMBER member) throws IOException {
        // 1. 파일 불러오기
        MultipartFile mLicence = member.getMLicence();

        // 2. 파일 이름 설정
        String originalFilename = mLicence.getOriginalFilename();

        // 3. 난수 설정
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // 4. 난수와 파일이름 합치기
        String mLicenceName = uuid + "_" + originalFilename;

        // 5. 파일 저장 위치
        /*String savePath = "C:/Users/82106/Desktop/승재/공부/오픈소스 강의/Intellij/Project/AllRight/src/main/resources/static/profile/"+mLicenceName;*/
        String savePath = "C:/Users/kijin/OneDrive/바탕 화면/승재/IdeaProjects/AllRight/src/main/resources/static/profile/"+mLicenceName;

        // 6. 파일 선택여부 확인
        if(!mLicence.isEmpty()) {
            member.setMLicenceName(mLicenceName);
            mLicence.transferTo(new File(savePath));
        }else {
            member.setMLicenceName("default.png");
        }
        int result = dao.mModify(member);

        if(result > 0) {
            mav.setViewName("redirect:/mView?mId="+member.getMId());
        } else {
            mav.setViewName("redirect:/mModiForm?mId="+member.getMId());
        }

        return mav;
    }

    // 회원삭제 메소드
    public ModelAndView mDelete(String mId) {
        int result = dao.mDelete(mId);

        if(result > 0 ) {
            mav.setViewName("index");
        } else {
            mav.setViewName("redirect:/mView?mId="+mId);
        }

        return mav;
    }

    // 즐겨찾기 목록 메소드
    public ModelAndView mfList(String fId) {

        List<FAVORITE> mFList = dao.mfList(fId);

        mav.setViewName("mfavoriteList");
        mav.addObject("mFList", mFList);

        return mav;
    }

    // 즐겨찾기 삭제
    public ModelAndView mfDelete(FAVORITE favorite) {
        int result = dao.mfDelete(favorite);

        if(result > 0 ) {
            mav.setViewName("redirect:/mfList?fId="+favorite.getFId());
        } else {
            mav.setViewName("redirect:/mPage");
        }

        return mav;
    }

    // 내 후기목록 조회
    public ModelAndView revList(String reId) {
        List<REVIEW> revlist = dao.revList(reId);

        mav.setViewName("revList");
        mav.addObject("revlist", revlist);

        return mav;
    }

    // 내 후기 상세조회
    public ModelAndView revView(int reNo) {

        REVIEW review = dao.revView(reNo);

        if(review!=null){
            mav.addObject("review", review);
            mav.setViewName("revView");
        } else {
            mav.setViewName("redirect:/index");
        }

        return mav;
    }

    // 문의 수정페이지로 이동
    public ModelAndView qModiForm(int qNo) {
        QUESTION question = dao.mqView(qNo);

        if(question!=null){
            mav.setViewName("qModify");
            mav.addObject("question",question);
        } else{
            mav.setViewName("redirect:/mqList?qId="+question.getQId());
        }
        return mav;
    }

    // 문의수정 메소드
    public ModelAndView qModify(QUESTION question) throws IOException {
        // (1) 파일 불러오기
        MultipartFile qFile = question.getQFile();

        // (2) 파일이름 설정하기
        String originalFileName = qFile.getOriginalFilename();

        // 스프링 파일 업로드 할 때 문제점! + 파일 이름이 같을 경우!

        // (3) 난수 생성하기
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // (4) 난수와 파일이름 합치기 : d8nd01_이상해씨.jpg
        String qFileName = uuid + "_" + originalFileName;

        // (5) 파일 저장위치
        String savePath = "C:/Users/82106/Desktop/승재/공부/오픈소스 강의/Intellij/Project/AllRight/src/main/resources/static/profile/"+qFileName;
        /*String savePath = "C:/Users/kijin/OneDrive/바탕 화면/승재/IdeaProjects/AllRight/src/main/resources/static/profile/"+qFileName;*/

        // (6) 파일 선택여부
        if(!qFile.isEmpty()){
            question.setQFilename(qFileName);
            qFile.transferTo(new File(savePath));
        } else{
            question.setQFilename("default.png");
        }

        // Q. 어떤 작업? 가입(입력)
        // Q. 입력, 수정, 삭제 시 int result 사용!
        int result = dao.qModify(question);

        if(result>0){
            // 성공
            mav.setViewName("redirect:/mqList?qId="+question.getQId());
        } else {
            // 실패
            mav.setViewName("redirect:/mqView?qNo="+question.getQNo());
        }

        return mav;
    }

    // 문의 삭제 메소드
    public ModelAndView qDelete(QUESTION question) {
        int result = dao.qDelete(question);

        if(result > 0 ) {
            mav.setViewName("redirect:/mqList?qNo="+question.getQId());
        } else {
            mav.setViewName("redirect:/mqView?qNo="+question.getQNo());
        }

        return mav;
    }

    // 후기 수정페이지로 이동
    public ModelAndView reModiForm(int reNo) {
        REVIEW review = dao.revView(reNo);

        if(review!=null){
            mav.setViewName("reModify");
            mav.addObject("review",review);
        } else{
            mav.setViewName("redirect:/revView?reNo="+review.getReNo());
        }
        return mav;
    }

    // 후기 수정
    public ModelAndView reModify(REVIEW review) throws IOException {
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
        int result = dao.reModify(review);

        if(result>0){
            // 성공
            mav.setViewName("redirect:/revList?reId="+review.getReId());
        } else {
            // 실패
            mav.setViewName("redirect:/revView?reNo="+review.getReNo());
        }

        return mav;
    }

    // 후기 삭제 메소드
    public ModelAndView reDelete(REVIEW review) {
        int result = dao.reDelete(review);

        if(result > 0 ) {
            mav.setViewName("redirect:/revList?reId="+review.getReId());
        } else {
            mav.setViewName("redirect:/revView?reNo?="+review.getReNo());
        }
        return mav;
    }

    // 만료데이터 조회 메소드
    public ModelAndView eList(String eId) {
        List<EXPIRATION> elist = dao.eList(eId);
        mav.setViewName("eList");
        mav.addObject("elist", elist);

        return mav;
    }

    // 만료데이터 상세 조회 메소드
    public ModelAndView eView(int eNo) {
        EXPIRATION expiration = dao.eView(eNo);

        if(expiration!=null){
            mav.addObject("expiration", expiration);
            mav.setViewName("eView");
        } else {
            mav.setViewName("redirect:/index");
        }

        return mav;
    }
}

