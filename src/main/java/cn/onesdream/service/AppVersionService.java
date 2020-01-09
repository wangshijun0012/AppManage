package cn.onesdream.service;

import cn.onesdream.pojo.AppVersion;

import java.util.List;

public interface AppVersionService {
    List<AppVersion> getAppVersion(String id);
    Boolean insertOne(AppVersion appVersion);

}
