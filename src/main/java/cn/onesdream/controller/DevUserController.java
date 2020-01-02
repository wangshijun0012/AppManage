package cn.onesdream.controller;

import cn.onesdream.service.DevUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/dev")
public class DevUserController {
    @Resource
    private DevUserService devUserService;
    @RequestMapping("/login")
    public String login(){
        return "/devlogin";
    }
    @RequestMapping("/dologin")
    public String dologin(HttpSession session, HttpServletRequest request){
        String devCode = request.getParameter("devCode");
        String devPassword = request.getParameter("devPassword");
        if(devUserService.isLogin(devCode, devPassword)){
            return "/developer/appinfolist";
        }else{
            request.setAttribute("error","用户名或密码错误！");
        }
        return "/devlogin";
    }

}
