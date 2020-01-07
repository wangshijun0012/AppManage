package cn.onesdream.service;

import cn.onesdream.dao.DevUserMapper;
import cn.onesdream.pojo.AppInfo;
import cn.onesdream.pojo.DevUser;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
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
    @Override
    public List<AppInfo> getTheList(HttpSession session, HttpServletRequest request){
        EntityWrapper wrapper = new EntityWrapper();
        String softwareName = request.getParameter("querySoftwareName");
        String queryStatus = request.getParameter("queryStatus");
        String queryFlatformId = request.getParameter("queryFlatformId");
        String queryCategoryLevel1 = request.getParameter("queryCategoryLevel1");
        String queryCategoryLevel2 = request.getParameter("queryCategoryLevel2");
        String queryCategoryLevel3 = request.getParameter("queryCategoryLevel3");
        if(softwareName != null && "".equals(softwareName)){
            wrapper.like("softwareName", softwareName);
            session.setAttribute("softwareName", softwareName);
        }
        if(queryStatus != null && "".equals(queryStatus)){

            session.setAttribute("queryStatus", queryStatus);
        }
        if(queryFlatformId != null && "".equals(queryFlatformId)){

            session.setAttribute("queryFlatformId", queryFlatformId);
        }
        if(queryCategoryLevel1 != null && "".equals(queryCategoryLevel1)){
            session.setAttribute("queryCategoryLevel1", queryCategoryLevel1);
        }
        if(queryCategoryLevel2 != null && "".equals(queryCategoryLevel2)){
            session.setAttribute("queryCategoryLevel2", queryCategoryLevel2);
        }
        if(queryCategoryLevel3 != null & "".equals(queryCategoryLevel3)){
            session.setAttribute("queryCategoryLevel3", queryCategoryLevel3);
        }
        return null;
    }


}
