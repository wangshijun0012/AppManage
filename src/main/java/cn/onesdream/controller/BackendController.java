package cn.onesdream.controller;

import cn.onesdream.pojo.*;
import cn.onesdream.service.*;
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
@RequestMapping("/backend")
public class BackendController {
  @Resource
  private BackendUserService backendUserService;
  @Resource
  private DataDictionaryService dataDictionaryService;
  @Resource
  private AppCategoryService appCategoryService;
  @Resource
  private AppInfoService appInfoService;
  @Resource
  private AppVersionService appVersionService;

  @RequestMapping("/login")
    public String loginback(){
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

  @RequestMapping("/main")
  public String main(HttpSession session, HttpServletRequest request){
    return "/backend/main";
  }

  @RequestMapping("/logout")
  public String logout(HttpSession  session){
    session.invalidate();
    return "/index";
  }

  //backend/app/list
  @RequestMapping("/app/list")
  public String list(HttpSession session,HttpServletRequest request){

    List<DataDictionary> allStatus = dataDictionaryService.getAllStatus();
    List<DataDictionary> allFlatForm = dataDictionaryService.getAllFlatForm();
    List list1 = (List) session.getAttribute("categoryLevel1List");
    List list2 = (List) session.getAttribute("categoryLevel2List");
    List list3 = (List) session.getAttribute("categoryLevel3List");
    if(list1 == null || "".equals(list1)){
      session.setAttribute("categoryLevel1List", appCategoryService.getLevel1());
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
    return "/backend/applist";
  }
  @RequestMapping("/app/categorylevellist.json")
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
  @RequestMapping("/app/datadictionarylist.json")
  @ResponseBody
  public Object dictionarylist(HttpSession session,HttpServletRequest request){
    List<DataDictionary> allFlatForm = dataDictionaryService.getAllFlatForm();
    return allFlatForm;
  }
//  /backend/app/check?aid=52&vid=38
  @RequestMapping("/app/check")
  public String check(HttpServletRequest request){
    String aid = request.getParameter("aid");
    String vid = request.getParameter("vid");


    AppVersion appVersion = appVersionService.getOneById(vid);
    AppInfo appInfo = appInfoService.getById(aid);
    appInfo.setCategoryLevel1Name(appCategoryService.getLevel1Name(appInfo.getCategoryLevel1()));
    appInfo.setCategoryLevel2Name(appCategoryService.getLevel2Name(appInfo.getCategoryLevel2()));
    appInfo.setCategoryLevel3Name(appCategoryService.getLevel3Name(appInfo.getCategoryLevel3()));
    appInfo.setFlatformName(dataDictionaryService.getFlatFormNameById(appInfo.getFlatformId()));
    request.setAttribute("appVersion",appVersion);
    request.setAttribute("appInfo",appInfo);
    return "/backend/appcheck";
  }

//  /backend/app/checksave?status=2&
  @RequestMapping("/app/checksave")
    public String checksave(HttpServletRequest request,AppInfo appInfo){
    String status = request.getParameter("status");
    String id = request.getParameter("id");
    if("2".equals(status)){
      appInfo.setStatus(2L);
      appInfoService.updateById(appInfo,id);
    }else if("3".equals(status)){
      appInfo.setStatus(3L);
      appInfoService.updateById(appInfo,id);
    }else{
      return "error";
    }
    return "redirect:/backend/app/list";
    }

}
