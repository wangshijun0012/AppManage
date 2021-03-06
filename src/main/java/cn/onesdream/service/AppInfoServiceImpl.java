package cn.onesdream.service;

import cn.onesdream.dao.AppInfoMapper;
import cn.onesdream.dao.AppVersionMapper;
import cn.onesdream.dao.DataDictionaryMapper;
import cn.onesdream.pojo.AppInfo;
import cn.onesdream.pojo.DataDictionary;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class AppInfoServiceImpl implements AppInfoService {
    private static int pageSize = 4;
    @Resource
    private AppInfoMapper appInfoMapper;
    @Resource
    private DataDictionaryMapper dataDictionaryMapper;
    @Override
    public Page<AppInfo> doAppListPage(HttpSession session, HttpServletRequest request){
        EntityWrapper wrapper = new EntityWrapper();
        String softwareName = request.getParameter("querySoftwareName");
        String queryStatus = request.getParameter("queryStatus");
        String queryFlatformId = request.getParameter("queryFlatformId");
        String queryCategoryLevel1 = request.getParameter("queryCategoryLevel1");
        String queryCategoryLevel2 = request.getParameter("queryCategoryLevel2");
        String queryCategoryLevel3 = request.getParameter("queryCategoryLevel3");

        if(softwareName != null && !"".equals(softwareName)){
            wrapper.like("softwareName", softwareName);
            request.setAttribute("softwareName", softwareName);
        }
        if(queryStatus != null && !"".equals(queryStatus)){
            wrapper.eq("status", queryStatus);
            request.setAttribute("queryStatus", queryStatus);
        }
        if(queryFlatformId != null && !"".equals(queryFlatformId)){
            wrapper.eq("flatformId",queryFlatformId);
            request.setAttribute("queryFlatformId", queryFlatformId);
        }
        if(queryCategoryLevel1 != null && !"".equals(queryCategoryLevel1)){
            wrapper.eq("categoryLevel1", queryCategoryLevel1);
            request.setAttribute("queryCategoryLevel1", queryCategoryLevel1);
        }
        if(queryCategoryLevel2 != null && !"".equals(queryCategoryLevel2)){
            wrapper.eq("categoryLevel2", queryCategoryLevel2);
            request.setAttribute("queryCategoryLevel2", queryCategoryLevel2);
        }
        if(queryCategoryLevel3 != null & !"".equals(queryCategoryLevel3)){
            wrapper.eq("categoryLevel3", queryCategoryLevel3);
            request.setAttribute("queryCategoryLevel3", queryCategoryLevel3);
        }
        String currentPageNoStr = request.getParameter("pageIndex");

        Integer currentPageNo = null;
        if(currentPageNoStr == null){
            currentPageNo = 1;
        }else{
            currentPageNo = Integer.parseInt(currentPageNoStr);
        }
        Page<AppInfo> page = new Page<AppInfo>(currentPageNo,pageSize);
        List<AppInfo> appInfos = appInfoMapper.MutilSelectList(page,wrapper);
        page.setRecords(appInfos);

        return page;
    }

    @Override
    public Boolean isExistByApk(String apkName) {
        EntityWrapper<AppInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("APKName", apkName);
        Integer count = appInfoMapper.selectCount(wrapper);
        if(count > 0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean addApp(AppInfo appInfo) {
        appInfo.setCreationDate(new Date(System.currentTimeMillis()));
        Integer insert = appInfoMapper.insert(appInfo);
        if(insert>0){
            return true;
        }
        return false;
    }

    @Override
    public AppInfo getById(String appId) {
        AppInfo appInfo = appInfoMapper.selectById(appId);
        DataDictionary dataDictionary = new DataDictionary();
        dataDictionary.setTypeCode("APP_STATUS");
        dataDictionary.setValueId(appInfo.getStatus());
        String valueName = dataDictionaryMapper.selectOne(dataDictionary).getValueName();
        appInfo.setStatusName(valueName);
        return appInfo;
    }

    @Override
    public Boolean updateById(AppInfo appInfo, String appId) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("id", appId);
        appInfo.setUpdateDate(new Date(System.currentTimeMillis()));
        Integer update = appInfoMapper.update(appInfo, wrapper);
        if(update > 0){
            return true;
        }
        return false;
    }

    @Override
    public AppInfo getMutilInfoById(String appId) {
        EntityWrapper<AppInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", appId);
        List<AppInfo> appInfos = appInfoMapper.MutilSelectList(new Page(), wrapper);
        AppInfo appInfo = appInfos.get(0);
        return appInfo;
    }

    @Override
    public Boolean delById(String appId) {
        Integer delete = appInfoMapper.deleteById(Long.parseLong(appId));
        if(delete > 0){
            return true;
        }
        return false;
    }
}
