package com.qf.controller;

import static com.qf.constant.SsmConstant.*;

import com.qf.constant.SsmConstant;
import com.qf.pojo.User;
import com.qf.service.UserService;
import com.qf.util.SendSMSUtil;
import com.qf.vo.PesultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.qf.constant.SsmConstant.CODE;

/**
 * @program: ssm
 * @description: 用户模块的controller层
 * @author: 狗十三
 * @create: 2019-07-15 10:28
 **/
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SendSMSUtil sendSMSUtil;

    //跳转路径
    @GetMapping("/register-ui")
    public String registerUI(){
        //转发到注册页面
        return "user/register";
    }
    //Request URL:http//localhost/user/check-username


    //1.json数据需要饭序列化成pojo对象
    //josnlib
    //jeckson
    // gson
    // fastJSON
    @PostMapping("/check-username")
    //不走视图解析器，直接响应  如果返回Map或者pojo累直接生成josn
    @ResponseBody
    public Map<String,Object> checkUsername(@RequestBody User user) {
     Integer count= userService.checkUsername(user.getUsername());

         //封装响应usermapper
        Map<String,Object> result  = new HashMap<>();
        result.put("code",0);
        result.put("msg","成功");
        //（0代表用户名可用，1代表用户名已被注册）
        result.put("data",count);
        return result;

    }
    @PostMapping(value = "/register",produces = "text/html;charset=utf-8")
    public String register(@Valid User user, BindingResult bindingResult, String register, String registerCode, HttpSession session,RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        //校验验证码
        if (!StringUtils.isEmpty(registerCode)) {
            //验证码不为空
            String code = (String) session.getAttribute(SsmConstant.CODE);
            if(!registerCode.equals(code)){
                redirectAttributes.addAttribute("registerInfo","验证码错误!");
                //验证码不正确
                return REDIRECT+REGISTER_UI;
            }
        }
        //校验验证码是否合法
             if (bindingResult.hasErrors() || StringUtils.isEmpty(registerCode)){
            //参数不合法
                 String msg = bindingResult.getFieldError().getDefaultMessage();
                 if (StringUtils.isEmpty(msg)){
                     msg = "验证码为必填项!";
                 }
                 redirectAttributes.addAttribute("registerInfo",msg);
                 return REDIRECT+REGISTER_UI;
             }
        //调用service执行注册
        Integer count = userService.register(user);

        //根据service返回结果跳转至指定页面
        if (count == 1){
            return REDIRECT + LOGIN_UI;
        }else {
            //注册失败
            redirectAttributes.addAttribute("registerInfo","服务器断开链接");
            return  REDIRECT + REGISTER_UI;
        }
    }
    @PostMapping(value = "/send-sms",produces = "text/html;charset=UTF-8")//通过produces = "text/charset = UTF-8"设置响应头信息，避免乱码
    @ResponseBody
    public String sendSMS(@RequestParam String phone,HttpSession session){
        //调用工具短信
        String result = sendSMSUtil.sendSMS(session, phone);
        return result;
    }


    @GetMapping("/login-ui")
    public String loginUI(){
        return "user/login";
    }


    //执行登录
    @PostMapping("/login")
    @ResponseBody
    public PesultVO login (String username, String password , HttpSession session){
        //校验参数是否合法
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) ){
            return new PesultVO(1,"用户名与密码位必填项",null);
        }
        //调用service执行登录
        User user = userService.login(username,password);
        //根据service返回结果判断登录能否成功
        if (user != null){
            //如果成功，将用户的信息放到session中
            session.setAttribute(USER,user);
            return  new PesultVO(0,"成功",null);
        }else {
            //如果失败，响应错误信息给ajax引擎
            return new PesultVO(2,"用户名或密码错误",null);
        }


    }
}
