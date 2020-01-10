package cn.onesdream.service;

import cn.onesdream.pojo.AppVersion;

import java.util.List;

public interface AppVersionService {
    List<AppVersion> getVersionByAppId(String appId);
    AppVersion getOneById(String versionId);
    Boolean insertOne(AppVersion appVersion, Long devUserId);
    Boolean updateById(AppVersion appVersion, String id);
    Boolean delByAppId(String appId);

}
