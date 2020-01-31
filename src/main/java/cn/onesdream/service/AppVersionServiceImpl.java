package cn.onesdream.service;

import cn.onesdream.dao.AppInfoMapper;
import cn.onesdream.dao.AppVersionMapper;
import cn.onesdream.dao.DataDictionaryMapper;
import cn.onesdream.pojo.AppInfo;
import cn.onesdream.pojo.AppVersion;
import cn.onesdream.pojo.DataDictionary;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AppVersionServiceImpl implements AppVersionService {
    @Resource
    private AppVersionMapper appVersionMapper;
    @Resource
    private AppInfoMapper appInfoMapper;
    @Resource
    private DataDictionaryMapper dataDictionaryMapper;
    @Override
    public List<AppVersion> getVersionByAppId(String appId) {
        EntityWrapper<AppVersion> versionWrapper = new EntityWrapper();
        versionWrapper.eq("appId", appId);
        List<AppVersion> versions = appVersionMapper.selectList(versionWrapper);
        AppInfo appInfo = appInfoMapper.selectById(appId);
        for(AppVersion appVersion:versions){
            Long status = appVersion.getPublishStatus();
            DataDictionary dictionary = new DataDictionary();
            dictionary.setTypeCode("PUBLISH_STATUS");
            dictionary.setValueId(status);
            DataDictionary dataDictionary = dataDictionaryMapper.selectOne(dictionary);
            appVersion.setAppName(appInfo.getSoftwareName());
            appVersion.setPublishStatusName(dataDictionary.getValueName());
        }

        return versions;
    }

    @Override
    public AppVersion getOneById(String versionId) {
        return appVersionMapper.selectById(versionId);
    }

    @Override
    public Boolean insertOne(AppVersion appVersion,Long devUserId) {
        Date currentTime = new Date(System.currentTimeMillis());
        appVersion.setCreatedBy(devUserId);
        appVersion.setCreationDate(currentTime);
        appVersion.setModifyDate(currentTime);
        Integer insert = appVersionMapper.insert(appVersion);
        AppInfo appInfo = new AppInfo();
        appInfo.setVersionId(appVersion.getId());
        appInfo.setUpdateDate(currentTime);
        appInfo.setSoftwareSize(appVersion.getVersionSize());
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("id", appVersion.getAppId());
        appInfoMapper.update(appInfo,wrapper);
        if(insert > 0 ){
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateById(AppVersion appVersion, String id) {
        EntityWrapper<AppVersion> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        appVersion.setModifyDate(new Date(System.currentTimeMillis()));
        Integer update = appVersionMapper.update(appVersion, wrapper);
        if(update > 0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean delByAppId(String appId) {
        EntityWrapper<AppVersion> wrapper = new EntityWrapper<AppVersion>();
        wrapper.eq("appId", appId);
        Integer delete = appVersionMapper.delete(wrapper);
        if(delete > 0){
            return true;
        }
        return false;
    }
}
