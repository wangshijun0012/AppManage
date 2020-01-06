package cn.onesdream.controller;

import cn.onesdream.pojo.AppCategory;
import cn.onesdream.pojo.DataDictionary;
import cn.onesdream.pojo.DevUser;
import cn.onesdream.service.AppCategoryService;
import cn.onesdream.service.DataDictionaryService;
import cn.onesdream.service.DevUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/dev")
public class DevController {
    @Resource
    private DevUserService devUserService;
    @Resource
    private DataDictionaryService dataDictionaryService;
    @Resource
    private AppCategoryService appCategoryService;
    @RequestMapping("/login")
    public String login(){
        return "/devlogin";
    }
    @RequestMapping("/dologin")
    public String dologin(HttpSession session, HttpServletRequest request){
        String devCode = request.getParameter("devCode");
        String devPassword = request.getParameter("devPassword");
        DevUser devUser = devUserService.getTheUser(devCode, devPassword);
        if(devUser != null){
            session.setAttribute("devUserSession", devUser);
            return "/developer/main";
        }else{
            request.setAttribute("error","用户名或密码错误！");
        }
        return "/devlogin";
    }
    @RequestMapping("/flatform/app/list")
    public String list(HttpSession session,HttpServletRequest request){
        String softwareName = request.getParameter("querySoftwareName");
        List<DataDictionary> allStatus = dataDictionaryService.getAllStatus();
        List<DataDictionary> allFlatForm = dataDictionaryService.getAllFlatForm();

        session.setAttribute("statusList",allStatus);
        session.setAttribute("flatFormList", allFlatForm);

        return "/developer/appinfolist";
    }
}