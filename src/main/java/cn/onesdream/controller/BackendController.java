package cn.onesdream.controller;

import cn.onesdream.pojo.BackendUser;
import cn.onesdream.service.BackendUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/backend")
public class BackendController {
  @Resource
  private BackendUserService backendUserService;
  @RequestMapping("/login")
    public String login(){
      return "/backendlogin";
  }
  @RequestMapping("/dologin")
  public String dologin(HttpServletRequest request, HttpSession session){
    String userCode = request.getParameter("userCode");
    String userPassword = request.getParameter("userPassword");
    BackendUser backendUser = backendUserService.getTheUser(userCode, userPassword);
    if(backendUser != null){
      session.setAttribute("userSession", backendUser);
      return "/backend/main";
    }else{
      request.setAttribute("error", "用户名或密码错误！");
    }
    return "/backendlogin";
  }

}
