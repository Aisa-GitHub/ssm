package com.qf.util;

import com.qf.constant.SsmConstant;
import com.qf.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @program: ssm
 * @description:
 * @author: 狗十三
 * @create: 2019-07-19 16:49
 **/

public class AuthenticationInterceptor  implements HandlerInterceptor{
     //执行controller方法
     //可以选择堆请求进行拦截/放行
     //return fale - > 拦截  return true ->放行

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        1.获得sessoin域中的用户信息
        HttpSession session = request.getSession();
         User user = (User) session.getAttribute(SsmConstant.USER);
//        2.判断用户是否为null
          if ( user == null){
//        3.如果为null - > 用户未登录 - > 跳转到登录页面 - > return false
              response.sendRedirect(request.getContextPath()+"/user/login-ui");
              return false;
          }else {
//        4.如果不为null  - > return true
              return  true;
          }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
