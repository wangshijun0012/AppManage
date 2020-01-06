package cn.onesdream.service;

import cn.onesdream.dao.BackendUserMapper;
import cn.onesdream.dao.DataDictionaryMapper;
import cn.onesdream.pojo.BackendUser;
import cn.onesdream.pojo.DataDictionary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class BackendUserServiceImpl implements BackendUserService {
    @Resource
    private BackendUserMapper backendUserMapper;
    @Resource
    private DataDictionaryMapper dataDictionaryMapper;
    @Override
    public BackendUser getTheUser(String username, String password) {
        if(username == null || "".equals(username) || password == null || "".equals(password)){
            return null;
        }

        BackendUser backendUser = new BackendUser();
        backendUser.setUserCode(username);
        backendUser.setUserPassword(password);
        BackendUser backendUser1 = backendUserMapper.selectOne(backendUser);

        DataDictionary dictionary = new DataDictionary();
        dictionary.setTypeCode("USER_TYPE");
        dictionary.setValueId(backendUser1.getUserType());
        backendUser1.setUserTypeName(dataDictionaryMapper.selectOne(dictionary).getValueName());

        return backendUser1;
    }
}
