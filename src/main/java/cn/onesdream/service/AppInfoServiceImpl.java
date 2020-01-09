package cn.onesdream.service;

import cn.onesdream.dao.AppInfoMapper;
import cn.onesdream.pojo.AppInfo;
import cn.onesdream.pojo.DataDictionary;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppInfoServiceImpl implements AppInfoService{
    @Resource
    private AppInfoMapper appInfoMapper;
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
