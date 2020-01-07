package cn.onesdream.service;

import cn.onesdream.pojo.AppInfo;
import cn.onesdream.pojo.DataDictionary;
import cn.onesdream.pojo.DevUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface DevUserService {

    DevUser getTheUser(String username,String password);
    List<AppInfo> getTheList(HttpSession session, HttpServletRequest request);
}
