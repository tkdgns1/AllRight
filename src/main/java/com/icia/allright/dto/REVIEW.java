package com.icia.allright.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("review")
public class REVIEW {
    private int reNo;
    private String reId;
    private String reTitle;
    private String reContent;
    private String rePname;
    private String reFilename;
    private MultipartFile reFile;
}
