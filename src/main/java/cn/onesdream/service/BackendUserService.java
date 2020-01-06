package cn.onesdream.service;

import cn.onesdream.pojo.BackendUser;

public interface BackendUserService {
    BackendUser getTheUser(String username,String password);

}
