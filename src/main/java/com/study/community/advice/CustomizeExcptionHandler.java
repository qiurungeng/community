package com.study.community.advice;

import com.alibaba.fastjson.JSON;
import com.study.community.dto.ResultDto;
import com.study.community.exception.CustomizeErrorCode;
import com.study.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExcptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    Object handle(HttpServletRequest request, HttpServletResponse response,
                  Throwable e, Model model) {
//        HttpStatus status = getStatus(request);
        String contentType=request.getContentType();
        if (contentType.equals("application/json")){
            //返回json
            ResultDto resultDto = (e instanceof CustomizeException?
                    ResultDto.errorOf((CustomizeException) e):
                    ResultDto.errorOf(CustomizeErrorCode.SYSTEM_ERROR));
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer=response.getWriter();
                writer.write(JSON.toJSONString(resultDto));
                writer.close();
            }catch (IOException ioe){}
            return null;
        }else {
            //错误页面跳转
            if (e instanceof CustomizeException){
                model.addAttribute("message",e. getMessage());
            }else {
                model.addAttribute("message","服务冒烟了，回家凉快去吧");
            }
            return new ModelAndView("error");
        }

    }

//    private HttpStatus getStatus(HttpServletRequest request) {
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        if (statusCode == null) {
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return HttpStatus.valueOf(statusCode);
//    }
}
