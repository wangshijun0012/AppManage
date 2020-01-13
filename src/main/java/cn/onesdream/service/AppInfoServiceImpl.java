package cn.onesdream.service;

import cn.onesdream.dao.AppInfoMapper;
import cn.onesdream.pojo.AppInfo;
import cn.onesdream.pojo.DataDictionary;
import cn.onesdream.tools.Constants;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class AppInfoServiceImpl implements AppInfoService {

    @Resource
    private AppInfoMapper appInfoMapper;

    @Override
    public Page<AppInfo> doAppListPage(HttpSession session, HttpServletRequest request) {
        EntityWrapper wrapper = new EntityWrapper();

        String softwareName = request.getParameter("querySoftwareName");//查询软件名称
        String queryStatus = request.getParameter("queryStatus");//查询状态
        String queryFlatformId = request.getParameter("queryFlatformId");//查询平台Id
        String queryCategoryLevel1 = request.getParameter("queryCategoryLevel1");//查询类别级别1
        String queryCategoryLevel2 = request.getParameter("queryCategoryLevel2");//查询类别级别2
        String queryCategoryLevel3 = request.getParameter("queryCategoryLevel3");//查询类别级别3

        if (softwareName != null && !"".equals(softwareName)) {
            wrapper.like("softwareName", softwareName);
            request.setAttribute("softwareName", softwareName);
        }
        if (queryStatus != null && !"".equals(queryStatus)) {
            wrapper.eq("queryStatus", queryStatus);
            request.setAttribute("queryStatus", queryStatus);
        }
        if (queryFlatformId != null && !"".equals(queryFlatformId)) {
            wrapper.eq("queryFlatformId", queryFlatformId);
            request.setAttribute("queryFlatformId", queryFlatformId);
        }
        if (queryCategoryLevel1 != null && !"".equals(queryCategoryLevel1)) {
            wrapper.eq("queryCategoryLevel1", queryCategoryLevel1);
            request.setAttribute("queryCategoryLevel1", queryCategoryLevel1);
        }
        if (queryCategoryLevel2 != null && !"".equals(queryCategoryLevel2)) {
            wrapper.eq("queryCategoryLevel2", queryCategoryLevel2);
            request.setAttribute("queryCategoryLevel2", queryCategoryLevel2);
        }
        if (queryCategoryLevel3 != null && !"".equals(queryCategoryLevel3)) {
            wrapper.eq("queryCategoryLevel3", queryCategoryLevel3);
            request.setAttribute("queryCategoryLevel3", queryCategoryLevel3);
        }

        String currentPageNoStr = request.getParameter("pageIndex");//当前页码 字符串
        Integer currentPageNo = null; //当前页码 如果为空赋为1 不为空 赋为 当前页码字符串
        if (currentPageNo == null) {
            currentPageNo = 1;
        } else {
            currentPageNo = Integer.parseInt(currentPageNoStr);
        }

        Page<AppInfo> page = new Page<AppInfo>(currentPageNo, Constants.pagesize);
        List<AppInfo> appInfos = appInfoMapper.MutilSelectList(page, wrapper);
        page.setRecords(appInfos);
        System.err.println(page);
        return page;
    }
    //审核
    @Override
    public boolean updateSatus(Long status, Integer id) throws Exception {
        boolean flag = false;
        EntityWrapper wrapper=new EntityWrapper();
        AppInfo appInfo=new AppInfo();
        appInfo.setStatus(status);
        wrapper.eq("id",id);

        if (appInfoMapper.update(appInfo,wrapper)==0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public Boolean isExistByApk(String apkName) {
        EntityWrapper<AppInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("APKName", apkName);
        Integer count = appInfoMapper.selectCount(wrapper);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean addApp(AppInfo appInfo) {
        Integer insert = appInfoMapper.insert(appInfo);
        if (insert > 0) {
            return true;
        }
        return false;

    }

    @Override
    public List<AppInfo> getAppInfoList(String softwareName, String status, String flatformId, String categoryLevel1, String categoryLevel2, String categoryLevel3, Integer currentPageNo, Integer pageSize) {
        return null;
    }

    @Override
    public int getAppInfoCount(String softwareName, String status, String flatformId, String categoryLevel1, String categoryLevel2, String categoryLevel3) {
        return 0;
    }

    @Override
    public List<DataDictionary> getDataDictionaryList(String typeCode) {
        return null;
    }

    @Override
    public boolean addAppInfo(AppInfo appInfo) {
        return false;
    }

    @Override
    public AppInfo getAppInfo(Integer id, String APKName) {
        return null;
    }

    @Override
    public AppInfo getAppInfoByID(Integer id) {
        return null;
    }

    @Override
    public boolean appinfomodifysave(AppInfo appInfo) {
        return false;
    }

    @Override
    public boolean deleteAppLogo(Integer id) {
        return false;
    }

    @Override
    public AppInfo getAppInfoById(Integer id) {
        return null;
    }

    @Override
    public boolean appsysdelAppInfo(Integer id) {
        return false;
    }

    @Override
    public boolean updateStatus(AppInfo appInfo) {
        return false;
    }
}
