package cn.onesdream.service;

import cn.onesdream.dao.DevUserMapper;
import cn.onesdream.pojo.DevUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class DevUserServiceImpl implements DevUserService {

    @Resource
    private DevUserMapper devUserMapper;
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
