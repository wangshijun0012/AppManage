package cn.onesdream.service;

import cn.onesdream.dao.AppInfoMapper;
import cn.onesdream.dao.DevUserMapper;
import cn.onesdream.pojo.AppInfo;
import cn.onesdream.pojo.DevUser;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class DevUserServiceImpl implements DevUserService {

    @Resource
    private DevUserMapper devUserMapper;
    @Resource
    private AppInfoMapper appInfoMapper;
    @Override
    public DevUser getTheUser(String username, String password) {
        if(username == null || "".equals(username) || password == null || "".equals(password)){
            return null;
        }
        DevUser devUser = new DevUser();
        devUser.setDevCode(username);
        devUser.setDevPassword(password);
        return devUserMapper.selectOne(devUser);
    }



}
