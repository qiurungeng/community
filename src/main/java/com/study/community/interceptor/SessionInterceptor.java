package com.study.community.interceptor;

import com.study.community.mapper.UserMapper;
import com.study.community.model.User;
import com.study.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器工作了"+request.getRequestURL());
        Cookie[] cookies=request.getCookies();
        User sessionUser = (User) request.getSession().getAttribute("user");
        User cookieUser=null;
        //看session中是否有已登录的User
        if (sessionUser!=null){
            return true;
        }
        //看Cookie中是否有已登录的User
        if(cookies!=null && cookies.length!=0){
            for (Cookie cookie:cookies) {
                if (cookie.getName().equals("token")){
                    String token=cookie.getValue();
                    UserExample userExample=new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                    cookieUser=userMapper.selectByExample(userExample).get(0);
                    if (cookieUser!=null){
                        request.getSession().setAttribute("user",cookieUser);
                    }
                    break;
                }
            }
            if (cookieUser==null){
                System.out.println("卡在这里了");
                response.sendRedirect("/");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
