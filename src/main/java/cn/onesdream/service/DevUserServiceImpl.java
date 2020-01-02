package cn.onesdream.service;

import cn.onesdream.dao.DevUserMapper;
import cn.onesdream.pojo.DevUser;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class DevUserServiceImpl implements DevUserService {
    @Resource
    private DevUserMapper devUserMapper;
    @Override
    public boolean isLogin(String username, String password) {
        if(username == null || "".equals(username) || password == null || "".equals(password)){
            return false;
        }
        DevUser devUser = new DevUser();
        devUser.setDevCode(username);
        devUser.setDevPassword(password);
        if(devUserMapper.selectOne(devUser) != null){
            return true;
        };
        return false;
    }
}
