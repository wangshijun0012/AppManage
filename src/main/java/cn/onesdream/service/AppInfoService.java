package cn.onesdream.service;

import cn.onesdream.pojo.AppInfo;
import com.baomidou.mybatisplus.plugins.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface AppInfoService {
    Page<AppInfo> doAppListPage(HttpSession session, HttpServletRequest request);
    Boolean isExistByApk(String apkName);
    Boolean addApp(AppInfo appInfo);
}
