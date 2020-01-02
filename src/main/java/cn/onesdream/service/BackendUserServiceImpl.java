package cn.onesdream.service;

import cn.onesdream.dao.BackendUserMapper;
import cn.onesdream.pojo.BackendUser;
import cn.onesdream.pojo.DevUser;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class BackendUserServiceImpl implements BackendUserService {
    @Resource
    private BackendUserMapper backendUserMapper;
    @Override
    public boolean isLogin(String username, String password) {

        if(username == null || "".equals(username) || password == null || "".equals(password)){
            return false;
        }
        BackendUser backendUser = new BackendUser();
        backendUser.setUserCode(username);
        backendUser.setUserPassword(password);
        if(backendUserMapper.selectOne(backendUser) != null){
            return true;
        };
        return false;
    }
}
