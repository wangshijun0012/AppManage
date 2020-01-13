package cn.onesdream.controller.Backend;

import cn.onesdream.pojo.AppCategory;
import cn.onesdream.pojo.AppInfo;
import cn.onesdream.pojo.DataDictionary;
import cn.onesdream.service.AppCategoryService;
import cn.onesdream.service.AppInfoService;
import cn.onesdream.service.DataDictionaryService;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * App审核后台控制层
 */
@Controller
@RequestMapping("APP")
public class AppInfoController {
    @Resource()
    private AppInfoService appInfoService;

    @Resource()
    private DataDictionaryService dataDictionaryService;

    @Resource()
    private AppCategoryService appCategoryService;

    //    查询所有类型编码为 的数据
    public List<DataDictionary> getDataDictionaryList(String typeCode) {
        List<DataDictionary> dataDictionary = null;
        dataDictionary = dataDictionaryService.getDataDictionaryList(typeCode);
        return dataDictionary;
    }

    // 获取类型列表
    public List<AppCategory> getCategoryList(String pid) {
        List<AppCategory> categoryLevelList = null;
        categoryLevelList = appCategoryService
                .getAppCategoryListByParentId(pid == null ? null : Integer.parseInt(pid));
        return categoryLevelList;
    }
    //查询出相应的分类级别列表
    @RequestMapping("/categorylevellist.json")
    public List<AppCategory> getAppCategoryList(String pid){
       if(pid.equals("")){
           pid=null;
       }
        return getCategoryList(pid);
    }
    //查询app
    @RequestMapping("appInfo/list")
    public String list(HttpSession session, HttpServletRequest request) {
        List<DataDictionary> allStatus = dataDictionaryService.getAllStatus();//查询APP状态
        List<DataDictionary> allFlatForm = dataDictionaryService.getAllFlatForm();//查询APP平台

        List list1 = (List) session.getAttribute("categoryLevel1List");
        List list2 = (List) session.getAttribute("categoryLevel2List");
        List list3 = (List) session.getAttribute("categoryLevel3List");

        if (list1 == null || "".equals(list1)) {
            session.setAttribute("categoryLevel1List", appCategoryService.getLevel1());
            System.err.println(session.getAttribute("categoryLevel1List"));
        }
        if (list2 == null || "".equals(list2)) {
            session.setAttribute("categoryLevel2List", appCategoryService.getLevel2());
            System.err.println(session.getAttribute("categoryLevel2List"));
        }
        if (list3 == null || "".equals(list3)) {
            session.setAttribute("categoryLevel3List", appCategoryService.getLevel3());
            System.err.println(session.getAttribute("categoryLevel3List"));
        }

        Page<AppInfo> page = appInfoService.doAppListPage(session, request);//页
        List<AppInfo> appInfos = page.getRecords();//app信息
        Long currentPageNo = null;//当前页码
        String pageIndex = request.getParameter("pageIndex");//页面索引

        if (pageIndex == null) {
            currentPageNo = 1L;
        } else {
            currentPageNo = Long.parseLong(pageIndex);
        }

        HashMap<String,Long> map=new HashMap<>();
        map.put("totalCount",page.getTotal());//总条数
        map.put("currentPageNo",currentPageNo);//当前页
        map.put("totalPageCount",page.getPages());//总页数

        session.setAttribute("pages",map);//页数
        session.setAttribute("appInfos",appInfos);//app信息
        session.setAttribute("allStatus",allStatus);//app状态
        session.setAttribute("allFlatForm",allFlatForm);//app平台

        return "/backend/applist";
    }

    @RequestMapping("/flatform/app/categorylevellist.json")
    @ResponseBody
    public Object levellist(HttpSession session,HttpServletRequest request){
        String pid = request.getParameter("pid");
        if (pid!=null&&!"".equals(pid)){
            List<AppCategory> levelByParent = appCategoryService.getLevelByParent(pid);
            return levelByParent;
        }else{
            return appCategoryService.getLevel1();
        }
    }
    /**
     * 跳转到审核页面
     * @param appId
     * @return
     */
    @RequestMapping("/toCheck/{appId}")
    public String toCheck(@PathVariable("appId") Long appId, Model model){
        AppInfo appInfo = appInfoService.selectAppInfoById(appId);
        model.addAttribute("appInfo",appInfo);
        return "check";
    }
}
