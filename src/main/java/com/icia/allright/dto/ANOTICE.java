package com.icia.allright.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("anotice")
public class ANOTICE {
    private int nNo;             // 게시글 번호
    private String nTitle;       // 글제목
    private String nContent;     // 글내용
    private MultipartFile nFile; // 파일
    private String nFileName;    // 파일 이름
}