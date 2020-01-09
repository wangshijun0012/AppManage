package cn.onesdream.service;

import cn.onesdream.dao.AppInfoMapper;
import cn.onesdream.dao.AppVersionMapper;
import cn.onesdream.dao.DataDictionaryMapper;
import cn.onesdream.pojo.AppInfo;
import cn.onesdream.pojo.AppVersion;
import cn.onesdream.pojo.DataDictionary;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppVersionServiceImpl implements AppVersionService {
    @Resource
    private AppVersionMapper appVersionMapper;
    @Resource
    private AppInfoMapper appInfoMapper;
    @Resource
    private DataDictionaryMapper dataDictionaryMapper;
    @Override
    public List<AppVersion> getAppVersion(String id) {
        EntityWrapper<AppVersion> versionWrapper = new EntityWrapper();
        versionWrapper.eq("appId", id);
        List<AppVersion> versions = appVersionMapper.selectList(versionWrapper);
        AppInfo appInfo = appInfoMapper.selectById(id);
        for(AppVersion appVersion:versions){
            Long status = appVersion.getPublishStatus();
            DataDictionary dictionary = new DataDictionary();
            dictionary.setTypeCode("PUBLISH_STATUS");
            dictionary.setValueId(status);
            DataDictionary dataDictionary = dataDictionaryMapper.selectOne(dictionary);
            appVersion.setAppName(appInfo.getSoftwareName());
            appVersion.setPublishStatusName(dataDictionary.getValueName());
            System.out.println(appVersion);
        }
        return versions;
    }

    @Override
    public Boolean insertOne(AppVersion appVersion) {
        Integer insert = appVersionMapper.insert(appVersion);
        if(insert > 0 ){
            return true;
        }
        return false;
    }
}
