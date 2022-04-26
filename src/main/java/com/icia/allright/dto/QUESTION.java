package com.icia.allright.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("question")
public class QUESTION {
    private int qNo;
    private String qId;
    private String qTitle;
    private String qContent;
    private String qPname;
    private String qFilename;
    private MultipartFile qFile;
}
