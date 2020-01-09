package cn.onesdream.controller;

import cn.onesdream.pojo.*;
import cn.onesdream.service.*;

import cn.onesdream.util.Data;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
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
    @Resource
    private AppInfoService appInfoService;
    @Resource
    private AppVersionService appVersionService;
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

        List<DataDictionary> allStatus = dataDictionaryService.getAllStatus();
        List<DataDictionary> allFlatForm = dataDictionaryService.getAllFlatForm();
        List list1 = (List) session.getAttribute("categoryLevel1List");
        List list2 = (List) session.getAttribute("categoryLevel2List");
        List list3 = (List) session.getAttribute("categoryLevel3List");
        if(list1 == null || "".equals(list1)){
            session.setAttribute("categoryLevel1List", appCategoryService.getLevel1());
            System.out.println(session.getAttribute("categoryLevel1List"));
        }
        if(list2 == null || "".equals(list2)){
            session.setAttribute("categoryLevel2List", appCategoryService.getLevel2());
        }
        if(list3 == null || "".equals(list3)){
            session.setAttribute("categoryLevel3List", appCategoryService.getLevel3());
        }
        Page<AppInfo> page = appInfoService.doAppListPage(session, request);
        List<AppInfo> appInfos = page.getRecords();
        Long currentPageNo = null;
        String pageIndex = request.getParameter("pageIndex");
        if(pageIndex == null){
            currentPageNo = 1L;
        }else{
            currentPageNo = Long.parseLong(pageIndex);
        }
        HashMap<String, Long> map = new HashMap<>();
        map.put("totalCount", page.getTotal());
        map.put("currentPageNo",currentPageNo);
        map.put("totalPageCount", page.getPages());
        session.setAttribute("pages", map);
        session.setAttribute("appInfoList", appInfos);
        session.setAttribute("statusList",allStatus);
        session.setAttribute("flatFormList", allFlatForm);
        return "/developer/appinfolist";
    }
    @RequestMapping("/flatform/app/categorylevellist.json")
    @ResponseBody
    public Object levellist(HttpSession session,HttpServletRequest request){
        String pid = request.getParameter("pid");
        if(pid != null && !"".equals(pid)) {
            List<AppCategory> levelByParent = appCategoryService.getLevelByParent(pid);
            return levelByParent;
        }else{
            return appCategoryService.getLevel1();
        }
    }
    @RequestMapping("/flatform/app/appinfoadd")
    public String appinfoadd(){
        return "/developer/appinfoadd";
    }


    @RequestMapping("/flatform/app/datadictionarylist.json")
    @ResponseBody
    public Object dictionarylist(HttpSession session,HttpServletRequest request){
        List<DataDictionary> allFlatForm = dataDictionaryService.getAllFlatForm();
        return allFlatForm;
    }

    @RequestMapping("/flatform/app/apkexist.json")
    @ResponseBody
    public Object apkexist(HttpSession session,HttpServletRequest request){
        String apkName = request.getParameter("APKName");
        Data data = new Data();
        if(apkName == null || "".equals(apkName)){
            return data.isEmpty();
        }else if(appInfoService.isExistByApk(apkName)){
            return data.isExist();
        }else{
            return data.isNoExist();
        }

    }

    @RequestMapping("/flatform/app/appinfoaddsave")
    public String appaddsave(HttpSession session, HttpServletRequest request,AppInfo appInfo){
        DevUser devUser = (DevUser) session.getAttribute("devUserSession");
        if(appInfo.getAPKName() != null){
            appInfo.setCreationDate(new Date(System.currentTimeMillis()));
            appInfo.setCreatedBy(devUser.getId());
            appInfoService.addApp(appInfo);
            return "redirect:/dev/flatform/app/list";
        }
        return "error";
    }

    @RequestMapping("/flatform/app/appversionadd")
    public String appversionadd(HttpServletRequest request,HttpSession session){
        String id = request.getParameter("id");
        List<AppVersion> appVersions = appVersionService.getAppVersion(id);
        request.setAttribute("appVersionList", appVersions);
        return "/developer/appversionadd";
    }

    @RequestMapping("/flatform/app/addversionsave")
    public String addversionsave(HttpServletRequest request,HttpSession session,AppVersion appVersion){
        appVersionService.insertOne(appVersion);
        return "redirect:/dev/flatform/app/list";
    }



}
