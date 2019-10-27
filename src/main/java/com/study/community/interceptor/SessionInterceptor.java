package com.study.community.interceptor;

import com.study.community.exception.CustomizeErrorCode;
import com.study.community.exception.CustomizeException;
import com.study.community.mapper.UserMapper;
import com.study.community.model.User;
import com.study.community.model.UserExample;
import com.study.community.service.NotificationService;
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
    @Autowired
    NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器工作了"+request.getRequestURL());
        Cookie[] cookies=request.getCookies();
        User sessionUser = (User) request.getSession().getAttribute("user");
        User cookieUser=null;
        //看session中是否有已登录的User
        if (sessionUser!=null){
            Long unreadCount=notificationService.unreadCount(sessionUser.getId());
            request.getSession().setAttribute("unreadCount",unreadCount);
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
                        Long unreadCount=notificationService.unreadCount(sessionUser.getId());
                        request.getSession().setAttribute("unreadCount",unreadCount);
                    }
                    break;
                }
            }

        }
        if (cookieUser==null&&sessionUser==null){
            System.out.println("用户未登录");
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
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
