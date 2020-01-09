package cn.onesdream.service;

import cn.onesdream.pojo.AppInfo;
import cn.onesdream.pojo.DevUser;
import com.baomidou.mybatisplus.plugins.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface DevUserService {

    DevUser getTheUser(String username,String password);
    Page<AppInfo> getTheList(HttpSession session, HttpServletRequest request);
}
