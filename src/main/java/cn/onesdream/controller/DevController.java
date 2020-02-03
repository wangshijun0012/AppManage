package cn.onesdream.controller;

import cn.onesdream.pojo.*;
import cn.onesdream.service.*;

import cn.onesdream.util.Data;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
    @RequestMapping("/flatform/main")
    public String main(HttpSession session, HttpServletRequest request){
        return "/developer/main";
    }

    @RequestMapping("logout")
    public String logout(HttpSession  session){
        session.invalidate();
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
            return data.apkIsEmpty();
        }else if(appInfoService.isExistByApk(apkName)){
            return data.apkIsExist();
        }else{
            return data.apkIsNoexist();
        }

    }

    @RequestMapping("/flatform/app/appinfoaddsave")
    public String appaddsave(HttpSession session, HttpServletRequest request, AppInfo appInfo, HttpServletResponse response,@RequestParam("a_logoPicPath") CommonsMultipartFile file) {
        if(appInfo.getAPKName() != null){
            if(!file.isEmpty() && file.getSize() < 500*1024){
                //获取文件名
                String name = file.getOriginalFilename();
                //允许的文件后缀名
                String[] suffixList = {"jpg","gif","png","jpeg","bmp"};
                //获取要上传的文件后缀名
                String suffixOfName = name.substring(name.lastIndexOf(".") + 1 ,name.length());
                //循环比较后缀名是否合法
                Boolean isImg = false;
                for(String str : suffixList){
                    if(str.equals(suffixOfName)){
                        InputStream fileInputStream = null;
                        try {
                            fileInputStream = file.getInputStream();
                            Image src = javax.imageio.ImageIO.read(fileInputStream); //构造Image对象
                            int wideth=src.getWidth(null); //得到源图宽
                            int height=src.getHeight(null); //得到源图长
                            //判断图片分辨率是否符合要求
                            if(wideth > 1024 || height > 1024 || wideth < 18 || height < 18){
                                isImg = false;
                            }else{
                                isImg = true;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fileInputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                }
                //后缀名不匹配则弹窗提示并返回页面
                if(isImg == false){
                    response.setContentType("text/html; charset=UTF-8"); //转码
                    PrintWriter out = null;
                    try {
                        out = response.getWriter();
                        out.flush();
                        out.println("<script>");
                        out.println("alert('文件类型或大小错误，请上传合法图片文件！');");
                        out.println("history.back();");
                        out.println("</script>");
                        return "/developer/appinfoadd";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        out.close();
                    }
                }
                //获取项目根目录
                String rootPath = request.getServletContext().getRealPath("/");
                String uploadPath = rootPath + "statics\\uploadfiles\\";
                //判断上传目录是否存在，否则创建上传目录
                File uploadDir = new File(uploadPath);
                if(!uploadDir.exists()){
                    uploadDir.mkdir();
                }
                //指定存放上传文件的目录及文件名
                String uploadFilePath = uploadPath + appInfo.getAPKName() + "." + suffixOfName;
                try {
                    file.transferTo(new File(uploadFilePath));//把文件写入目标文件地址
                    DevUser devUser = (DevUser) session.getAttribute("devUserSession");
                    appInfo.setLogoPicPath("/statics/uploadfiles/" + appInfo.getAPKName() + "." + suffixOfName );
                    appInfo.setLogoLocPath(uploadFilePath);
                    appInfo.setDevId(devUser.getId());
                    appInfo.setCreatedBy(devUser.getId());
                    appInfoService.addApp(appInfo);
                    return "redirect:/dev/flatform/app/list";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "error";
                }
            }
            return "redirect:/dev/flatform/app/list";
        }
        return "error";
    }

    @RequestMapping("/flatform/app/appversionadd")
    public String appversionadd(HttpServletRequest request,HttpSession session){
        String id = request.getParameter("id");
        List<AppVersion> appVersions = appVersionService.getVersionByAppId(id);
        request.setAttribute("appId", id);
        request.setAttribute("appVersionList", appVersions);
        return "/developer/appversionadd";
    }

    @RequestMapping("/flatform/app/addversionsave")
    public String addversionsave(HttpServletRequest request, HttpServletResponse response,HttpSession session, AppVersion appVersion,@RequestParam("a_downloadLink") CommonsMultipartFile file){
        if(!file.isEmpty()){
            String name = file.getOriginalFilename();
            String suffixOfName = name.substring(name.lastIndexOf(".") + 1 ,name.length());
            String rootPath = request.getServletContext().getRealPath("/");
            AppInfo appInfo = appInfoService.getById(appVersion.getAppId().toString());
            String uploadFilePath = rootPath + "statics\\uploadfiles\\" + appInfo.getAPKName() + "-" + appVersion.getVersionNo() + "." + suffixOfName;
            if("apk".equals(suffixOfName)){
                try {
                    file.transferTo(new File(uploadFilePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                response.setContentType("text/html; charset=UTF-8"); //转码
                PrintWriter out = null;
                try {
                    out = response.getWriter();
                    out.flush();
                    out.println("<script>");
                    out.println("alert('文件类型或大小错误，请上传合法apk文件！');");
                    out.println("history.back();");
                    out.println("</script>");
                    return "/developer/appversionadd";
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    out.close();
                }
            }
            DevUser devUserSession = (DevUser) session.getAttribute("devUserSession");
            Long devUserId = devUserSession.getId();
            appVersion.setApkLocPath(uploadFilePath);
            appVersion.setDownloadLink("/statics/uploadfiles/" + appInfo.getAPKName() + "-" + appVersion.getVersionNo() + "." + suffixOfName );
            appVersion.setApkFileName(appInfo.getAPKName() + "-" + appVersion.getVersionNo() + "." + suffixOfName);
            appVersionService.insertOne(appVersion,devUserId);
            return "redirect:/dev/flatform/app/list";
        }
        return "error";
    }
    //appversionmodify?vid=45&aid=51
    @RequestMapping("/flatform/app/appversionmodify")
    public String appversionmodify(HttpServletRequest request,HttpSession session){

        List<AppVersion> appVersions = appVersionService.getVersionByAppId(request.getParameter("aid"));
        request.setAttribute("appVersionList",appVersions);
        AppVersion appVersion = appVersionService.getOneById(request.getParameter("vid"));
        request.setAttribute("appVersion", appVersion);
        return "/developer/appversionmodify";
    }

//    appversionmodifysave
    @RequestMapping("/flatform/app/appversionmodifysave")
    public String appversionmodifysave(HttpServletRequest request,HttpSession session,HttpServletResponse response,AppVersion appVersion,@RequestParam("attach") CommonsMultipartFile file){
        if(!file.isEmpty()){
            String name = file.getOriginalFilename();
            String suffixOfName = name.substring(name.lastIndexOf(".") + 1 ,name.length());
            String rootPath = request.getServletContext().getRealPath("/");
            AppInfo appInfo = appInfoService.getById(appVersion.getAppId().toString());
            String uploadFilePath = rootPath + "statics\\uploadfiles\\" + appInfo.getAPKName() + "-" + appVersion.getVersionNo() + "." + suffixOfName;
            if("apk".equals(suffixOfName)){
                try {
                    file.transferTo(new File(uploadFilePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                response.setContentType("text/html; charset=UTF-8"); //转码
                PrintWriter out = null;
                try {
                    out = response.getWriter();
                    out.flush();
                    out.println("<script>");
                    out.println("alert('文件类型或大小错误，请上传合法apk文件！');");
                    out.println("history.back();");
                    out.println("</script>");
                    return "/developer/appversionmodify";
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    out.close();
                }
            }
            String versionId = request.getParameter("id");
            appVersion.setApkLocPath(uploadFilePath);
            appVersion.setDownloadLink("/statics/uploadfiles/" + appInfo.getAPKName() + "-" + appVersion.getVersionNo() + "." + suffixOfName );
            appVersion.setApkFileName(appInfo.getAPKName() + "-" + appVersion.getVersionNo() + "." + suffixOfName);
            appVersionService.updateById(appVersion, versionId);
            appInfo.setSoftwareSize(appVersion.getVersionSize());
            appInfoService.updateById(appInfo, appVersion.getAppId().toString());
            return "redirect:/dev/flatform/app/list";
        }
        return "error";
    }
//    delfile.json
    @RequestMapping("/flatform/app/delfile.json")
    @ResponseBody
    public Object delfile(HttpServletRequest request,HttpSession session){
        String id = request.getParameter("id");
        String flag = request.getParameter("flag");
        if(flag.equals("apk")) {
            AppVersion appVersion = appVersionService.getOneById(id);
            //特别注意以下两行，需要在applicationContext.xml里面的globalConfiguration类中添加对应属性
            appVersion.setDownloadLink("");
            appVersion.setApkLocPath("");
            appVersion.setApkFileName("");
            if (appVersionService.updateById(appVersion, id)) {
                return new Data().success();
            }
            return new Data().failed();
        }else if(flag.equals("logo")){
            AppInfo appInfo = appInfoService.getById(id);
            appInfo.setLogoPicPath("");
            appInfo.setLogoLocPath("");
            if(appInfoService.updateById(appInfo,id)){
                return new Data().success();
            }
            return new Data().failed();
        }
        return "error";
    }
//    /flatform/app/appinfomodify?id=50
    @RequestMapping("/flatform/app/appinfomodify")
    public String appinfomodify(HttpServletRequest request,HttpSession session){
        String id = request.getParameter("id");
        AppInfo appInfo = appInfoService.getById(id);
        request.setAttribute("appInfo",appInfo);
        return "/developer/appinfomodify";

    }
//    appinfomodifysave
    @RequestMapping("/flatform/app/appinfomodifysave")
    public String appinfomodifysava(HttpServletRequest request,HttpSession session,HttpServletResponse response,AppInfo appInfo,@RequestParam("attach") CommonsMultipartFile file){
        if(appInfo.getAPKName() != null){
            if(!file.isEmpty() && file.getSize() < 500*1024){
                //获取文件名
                String name = file.getOriginalFilename();
                //允许的文件后缀名
                String[] suffixList = {"jpg","gif","png","jpeg","bmp"};
                //获取要上传的文件后缀名
                String suffixOfName = name.substring(name.lastIndexOf(".") + 1 ,name.length());
                //循环比较后缀名是否合法
                Boolean isImg = false;
                for(String str : suffixList){
                    if(str.equals(suffixOfName)){
                        InputStream fileInputStream = null;
                        try {
                            fileInputStream = file.getInputStream();
                            Image src = javax.imageio.ImageIO.read(fileInputStream); //构造Image对象
                            int wideth=src.getWidth(null); //得到源图宽
                            int height=src.getHeight(null); //得到源图长
                            //判断图片分辨率是否符合要求
                            if(wideth > 1024 || height > 1024 || wideth < 18 || height < 18){
                                isImg = false;
                            }else{
                                isImg = true;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fileInputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                }
                //后缀名不匹配则弹窗提示并返回页面
                if(isImg == false){
                    response.setContentType("text/html; charset=UTF-8"); //转码
                    PrintWriter out = null;
                    try {
                        out = response.getWriter();
                        out.flush();
                        out.println("<script>");
                        out.println("alert('文件类型或大小错误，请上传合法图片文件！');");
                        out.println("history.back();");
                        out.println("</script>");
                        return "/developer/appinfoadd";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        out.close();
                    }
                }
                //获取项目根目录
                String rootPath = request.getServletContext().getRealPath("/");
                String uploadPath = rootPath + "statics\\uploadfiles\\";
                //判断上传目录是否存在，否则创建上传目录
                File uploadDir = new File(uploadPath);
                if(!uploadDir.exists()){
                    uploadDir.mkdir();
                }
                //指定存放上传文件的目录及文件名
                String uploadFilePath = uploadPath + appInfo.getAPKName() + "." + suffixOfName;
                try {
                    file.transferTo(new File(uploadFilePath));//把文件写入目标文件地址
                    DevUser devUser = (DevUser) session.getAttribute("devUserSession");
                    appInfo.setLogoPicPath("/statics/uploadfiles/" + appInfo.getAPKName() + "." + suffixOfName );
                    appInfo.setLogoLocPath(uploadFilePath);
                    appInfo.setModifyBy(devUser.getId());
                    appInfo.setModifyDate(new Date(System.currentTimeMillis()));
                    if( !appInfoService.updateById(appInfo,appInfo.getId().toString())){
                        return "error";
                    }
                    return "redirect:/dev/flatform/app/list";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "error";
                }
            }
            return "error";
        }

        return "error";
    }
//    appview?id=51
    @RequestMapping("/flatform/app/appview")
    public String appview(HttpServletRequest request,HttpSession session){
        String id = request.getParameter("id");
        AppInfo appInfo = appInfoService.getMutilInfoById(id);
        List<AppVersion> appVersions = appVersionService.getVersionByAppId(id);
        request.setAttribute("appInfo",appInfo);
        request.setAttribute("appVersionList",appVersions);
        return "/developer/appinfoview";
    }
//    delapp.json
    @RequestMapping("/flatform/app/delapp.json")
    @ResponseBody
    public Object delapp(HttpServletRequest request,HttpSession session){
        String id = request.getParameter("id");
        if(appInfoService.delById(id)){
            appVersionService.delByAppId(id);
            return new Data().delIsTrue();
        }
        return new Data().delIsNotExist();
    }
    ///sale.json
    @RequestMapping("/flatform/app/sale.json")
    @ResponseBody
    public Object sale(HttpServletRequest request,HttpSession session){
        String id = request.getParameter("appId");
        if(id != null){
            AppInfo appInfo = appInfoService.getById(id);

            Long status = appInfo.getStatus();
            if(status == 5L ){
                appInfo.setStatus(4L);
                appInfoService.updateById(appInfo,id);
                return new Data().error0().success();
            }else if(status == 4L){
                appInfo.setStatus(5L);
                appInfoService.updateById(appInfo,id);
                return new Data().error0().success();
            }else{
                return new Data().failed().errorparam000001();
            }

        }else{
            return new Data().errorexception000001().failed();
        }


    }

}
