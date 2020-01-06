package cn.onesdream.service;

import cn.onesdream.pojo.DataDictionary;
import cn.onesdream.pojo.DevUser;

import java.util.List;

public interface DevUserService {

    DevUser getTheUser(String username,String password);

}
