package com.icia.allright.service;

import com.icia.allright.dao.ADAO;
import com.icia.allright.dao.RDAO;
import com.icia.allright.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AService {

    @Autowired
    private ADAO dao;

    @Autowired
    private RDAO rdao;

    @Autowired
    private HttpServletResponse response;

    private ModelAndView mav = new ModelAndView();

    // 주차장 추가
    public ModelAndView pAdd(PARKINGLOT parkinglot){

        // 주소 합치기
        parkinglot.setPAddr("("+parkinglot.getAddr1()+") " + parkinglot.getAddr2() + ", " + parkinglot.getAddr3());

        int result = dao.pAdd(parkinglot);

        if(result > 0) {
            mav.setViewName("index");
        }else {
            mav.setViewName("PaddForm");
        }
        return mav;
    }

    // 공지사항 작성 메소드
    public ModelAndView nWrite(ANOTICE anotice) throws IOException {
        // 1. 파일 불러오기
        MultipartFile nFile = anotice.getNFile();

        // 2. 원본 파일 이름 가져오기
        String originalFileName = nFile.getOriginalFilename();

        // 3. 랜덤한 문자열 만들기
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // 4. 3번(난수)과 2번(원본파일이름) 합치기!
        String nFileName = uuid + "_" + originalFileName;

        // 5. 파일 저장위치
        /*String savePath = "C:/Users/82106/Desktop/승재/공부/오픈소스 강의/Intellij/Project/AllRight/src/main/resources/static/img/"+nFileName;*/
        String savePath = "C:/Users/kijin/OneDrive/바탕 화면/승재/IdeaProjects/AllRight/src/main/resources/static/img/"+nFileName;

        // 6. 파일 선택여부
        if(!nFile.isEmpty()){
            anotice.setNFileName(nFileName);
            nFile.transferTo(new File(savePath));
        }

        // Q. 입력, 수정, 삭제 시 필요한 데이터타입과 변수는?
        int result = dao.nWrite(anotice);

        // 성공하면 result = 1 , 실패하면 result = 0

        if(result>0){
            mav.setViewName("redirect:/nList");
        } else {
            mav.setViewName("nWriteForm");
        }

        return mav;
    }

    // 공지사항 목록조회
    public ModelAndView nList() {
        // 상세보기 : DTO
        // 목록보기 : List<DTO>

        List<ANOTICE> anoticeList = dao.nList();

        mav.setViewName("nList");
        mav.addObject("anoticeList",anoticeList);

        return mav;
    }

    // 공지사항 상세 조회
    public ModelAndView nView(int nNo) {
        
        ANOTICE anotice = dao.nView(nNo);

        if(anotice!=null){
            mav.addObject("anotice", anotice);
            mav.setViewName("nView");
        } else {
            mav.setViewName("redirect:/nList");
        }

        return mav;
    }

    // 공지수정페이지로 이동
    public ModelAndView nModiForm(int nNo) {
        // 상세보기 때 만들어 놓은 bView(bNo)메소드 사용
        ANOTICE anotice = dao.nView(nNo);

        if(anotice!=null){
            mav.addObject("anotice", anotice);
            mav.setViewName("nModify");
        } else {
            mav.setViewName("redirect:/nList");
        }

        return mav;
    }
    
    // 공지 수정
    public ModelAndView nModify(ANOTICE anotice) throws IOException {
        // 1. 파일 불러오기
        MultipartFile nFile = anotice.getNFile();

        // 2. 원본 파일 이름 가져오기
        String originalFileName = nFile.getOriginalFilename();

        // 3. 랜덤한 문자열 만들기
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // 4. 3번(난수)과 2번(원본파일이름) 합치기!
        String nFileName = uuid + "_" + originalFileName;

        // 5. 파일 저장위치
        /*String savePath = "C:/Users/82106/Desktop/승재/공부/오픈소스 강의/Intellij/Project/AllRight/src/main/resources/static/img/"+nFileName;*/
        String savePath = "C:/Users/kijin/OneDrive/바탕 화면/승재/IdeaProjects/AllRight/src/main/resources/static/img/"+nFileName;

        // 6. 파일 선택여부
        if(!nFile.isEmpty()){
            anotice.setNFileName(nFileName);
            nFile.transferTo(new File(savePath));
        }

        // Q. 입력, 수정, 삭제 시 필요한 데이터타입과 변수는?
        int result = dao.nModify(anotice);

        // 성공하면 result = 1 , 실패하면 result = 0

        if(result>0){
            mav.setViewName("redirect:/nView?nNo="+anotice.getNNo());
        } else {
            mav.setViewName("redirect:/nModiForm?nNo="+anotice.getNNo());
        }

        return mav;
    }

    // 공지사항 삭제
    public ModelAndView nDelete(int nNo) {
        int result = dao.nDelete(nNo);

        if(result>0){
            mav.setViewName("redirect:/nList");
        } else {
            mav.setViewName("redirect:/nView?nNo=" + nNo);
        }

        return mav;
    }

    // 문의내역 전체 조회
    public ModelAndView aQlist() {
        System.out.println("aQlist Service");
        List<QUESTION> questList = dao.aQlist();

        System.out.println("aQlist Service2 = " + questList);

        mav.setViewName("aqList");
        mav.addObject("question",questList);

        return mav;
    }

    // 회원목록보기
    public ModelAndView aMlist() {
        System.out.println("[2] service");

        // 목록 -> List, 상세 -> DTO
        List<MEMBER> memberList = dao.aMlist();

        System.out.println("[3] service : memberList -> " + memberList);

        mav.setViewName("amList");
        mav.addObject("memberList", memberList);

        return mav;
    }

    // 게시글 댓글 조회
    public List<QCOMMENT> cList(int CBNo) {
        List<QCOMMENT> commentList = dao.cList(CBNo);
        return commentList;
    }

    // 게시판 댓글 작성
    public List<QCOMMENT> cWrite(QCOMMENT comment) {
        List<QCOMMENT> commentList = null;
        int result = dao.cWrite(comment);

        if(result>0) {
            commentList = dao.cList(comment.getQCbno());
        } else {
            commentList = null;
        }
        return commentList;
    }

    // 게시판 댓글 삭제
    public List<QCOMMENT> cDelete(QCOMMENT comment) {
        System.out.println("[2]Service 댓글삭제 service: " + comment);
        List<QCOMMENT> commentList = null;
        int result = dao.cDelete(comment);
        System.out.println("[3]Service 댓글삭제 result값이?: " + result);
        System.out.println("[3]Service 댓글삭제 qcbno?: " + comment.getQCbno());
        if(result>0) {
            commentList = dao.cList(comment.getQCbno());
        } else {
            commentList = null;
        }
        return commentList;
    }

    // 주차장 리스트 조회
    public ModelAndView aPlist() {
        List<PARKINGLOT> pList = dao.aPlist();

        mav.setViewName("pList");
        mav.addObject("pList",pList);

        return mav;
    }

    // 하루 지난 예약목록 조회
    public ModelAndView aPdata() {
        List<RESERVE> rdList = dao.aPdata();

        mav.setViewName("rdList");
        mav.addObject("rdList",rdList);

        return mav;
    }

    // 만료 데이터 삭제
    public ModelAndView rdDelete(int rNo, String rPname) throws IOException {
        int result = dao.rdDelete(rNo);
        int result2 = dao.plus(rPname);

        int result3 = result + result2;

        if (result3 <= 1) {
            response.setContentType("text/html; charset=UTF-8");

            PrintWriter out = response.getWriter();

            out.println("<script>alert('삭제실패!');</script>");
        }
        mav.setViewName("rdList");

        return mav;
    }

    public ModelAndView autodelete() throws IOException {
        List<RESERVE> rdList = dao.aPdata();
        if(rdList!=null){
            ArrayList<Integer>  autonolist = new ArrayList<>();
            ArrayList<String>  autonamelist = new ArrayList<>();

            for(var i=0; i<rdList.size(); i++){
                autonolist.add(rdList.get(i).getRNo());
                autonamelist.add(rdList.get(i).getRPname());
            }

            for(var i=0; i<autonolist.size(); i++){
                RESERVE RESERVE = rdao.rView(autonolist.get(i));
                EXPIRATION expiration = new EXPIRATION();
                expiration.setENo(RESERVE.getRNo());
                expiration.setEId(RESERVE.getRId());
                expiration.setEPcode(RESERVE.getRPcode());
                expiration.setEPname(RESERVE.getRPname());
                expiration.setEPaddr(RESERVE.getRPaddr());
                expiration.setEPrice(RESERVE.getRPrice());
                expiration.setEDate(RESERVE.getRDate());
                int result1 = dao.rmove(expiration);
                int result2 = dao.rdDelete(autonolist.get(i));
                int result3 = dao.plus(autonamelist.get(i));

                int result4 = result1 + result2 + result3;
                if (result4 <= 1) {
                    response.setContentType("text/html; charset=UTF-8");

                    PrintWriter out = response.getWriter();

                    out.println("<script>alert('삭제실패!');</script>");
                    mav.setViewName("redirect:/aPdata");
                }
        }
            System.out.println("AutoDelete 실행!");
            mav.setViewName("index");
        }
        return mav;
    }
}
