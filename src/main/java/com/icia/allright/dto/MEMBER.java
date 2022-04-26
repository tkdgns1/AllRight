package com.icia.allright.dto;


import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("member")
public class MEMBER {
    private String mId;
    private String mPw;
    private String mName;
    private String mBirth;
    private String mPhone;
    private String mLicenceName;
    private MultipartFile mLicence;
}