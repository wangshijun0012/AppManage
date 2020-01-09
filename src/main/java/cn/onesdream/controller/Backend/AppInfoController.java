package cn.onesdream.controller.Backend;

import cn.onesdream.pojo.AppCategory;
import cn.onesdream.pojo.DataDictionary;
import cn.onesdream.service.AppCategoryService;
import cn.onesdream.service.AppInfoService;
import cn.onesdream.service.DataDictionaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
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
//    public List<AppCategory> getCategoryList(String pid) {
//        List<AppCategory> categoryLevelList = null;
//        try {
//            categoryLevelList = appCategoryService
//                    .getAppCategoryListByParentId(pid == null ? null : Integer
//                            .parseInt(pid));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return categoryLevelList;
//    }
    public List<AppCategory> getCategoryList(String pid) {
        List<AppCategory> categoryLevelList = null;
        categoryLevelList = appCategoryService
                .getAppCategoryListByParentId(pid == null ? null : Integer.parseInt(pid));
        return categoryLevelList;
    }

    @RequestMapping("list")
    public String list() {

        return "/backend/applist";
    }
}
