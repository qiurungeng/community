package com.study.community.controller;

import com.study.community.dto.FileDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FieController {
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDto upload(){
        FileDto fileDto=new FileDto();
        fileDto.setSuccess(1);
        fileDto.setUrl("/images/wini.jpg");
        return fileDto;
    }

}
