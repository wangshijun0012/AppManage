package cn.onesdream.service;

import cn.onesdream.pojo.AppInfo;
import cn.onesdream.pojo.DataDictionary;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.List;

/**
 * app信息 业务层
 */
public interface AppInfoService {
    /**
     * 更新app状态/审核
     *
     * @param status
     *
     * @param id
     *
     * @return
     *
     * @throws Exception
     */
    boolean updateSatus(Long status, Integer id) throws Exception;
    /**
     *
     * @param apkName
     * @return
     */
    Boolean isExistByApk(String apkName);

    /**
     *APP列表页面
     * @param session
     * @param request
     * @return
     */
    Page<AppInfo> doAppListPage(HttpSession session, HttpServletRequest request);

    /**
     *
     * @param appInfo
     * @return
     */
    Boolean addApp(AppInfo appInfo);
    /**
     * 按条件查询appinfo
     * @param softwareName 软件名
     * @param status 状态
     * @param categoryLevel1 一级分类
     * @param categoryLevel2 二级分类
     * @param categoryLevel3 三级分类
     * @param flatformId    所属平台Id
     * @param currentPageNo 当前页
     * @param pageSize 页大小
     * @return appinfoList
     */
    List<AppInfo> getAppInfoList(String softwareName, String status,String flatformId,
                                 String categoryLevel1, String categoryLevel2,
                                 String categoryLevel3, Integer currentPageNo,
                                 Integer pageSize);

    /**
     * 获取appInfo的数量
     * @param softwareName 软件名
     * @param status 状态
     * @param categoryLevel1 一级分类
     * @param categoryLevel2 二级分类
     * @param categoryLevel3 三级分类
     * @return appInfoCount
     */
    int getAppInfoCount(String softwareName, String status,String flatformId,
                        String categoryLevel1, String categoryLevel2,
                        String categoryLevel3);

    /**
     * 根据typeCode查询出相应的数据字典列表
     * @param typeCode typeCode
     * @return 数据字典列表
     */
    List<DataDictionary> getDataDictionaryList(String typeCode);

    /**
     * 新增app基本信息
     * @param appInfo appInfo
     * @return boolean
     */
    boolean addAppInfo(AppInfo appInfo);
    /**
     * 根据id、apkName查找appInfo
     * @param id id
     * @return appInfo
     */
    AppInfo getAppInfo(Integer id, String APKName);

    /**
     * 根据id查询appinfo
     * @param id id
     * @return AppInfo
     */
    AppInfo getAppInfoByID(Integer id);

    /**
     * 修改appinfo
     * @param appInfo Appinfo
     * @return boolean
     */
    boolean appinfomodifysave(AppInfo appInfo);

    /**
     *  删除logo图片
     * @param id id
     * @return boolean
     */
    boolean deleteAppLogo(Integer id);
    /**
     * 获取app基础信息
     * @param id id
     * @return AppInfo
     */
    AppInfo getAppInfoById(@Param(value = "id") Integer id);

    /**
     * 删除app信息
     * @param id id
     * @return boolean
     */
    boolean appsysdelAppInfo(@Param("id") Integer id);
    /**
     * 更新app状态
     * @return boolean
     */
    boolean updateStatus(AppInfo appInfo);

}
