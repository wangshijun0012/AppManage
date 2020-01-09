package cn.onesdream.controller;

import cn.onesdream.pojo.AppCategory;
import cn.onesdream.pojo.AppInfo;
import cn.onesdream.pojo.DataDictionary;
import cn.onesdream.pojo.DevUser;
import cn.onesdream.service.AppCategoryService;
import cn.onesdream.service.DataDictionaryService;
import cn.onesdream.service.DevUserService;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
            session.setAttribute("categoryLevel1List", appCategoryService.getLevel1(request));
            System.out.println(session.getAttribute("categoryLevel1List"));
        }
        if(list2 == null || "".equals(list2)){
            session.setAttribute("categoryLevel2List", appCategoryService.getLevel2(request));
        }
        if(list3 == null || "".equals(list3)){
            session.setAttribute("categoryLevel3List", appCategoryService.getLevel3());
        }
        Page<AppInfo> page = devUserService.getTheList(session, request);
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
        System.out.println(pid);
        List<AppCategory> levelByParent = appCategoryService.getLevelByParent(pid);

        return levelByParent;

    }

}
