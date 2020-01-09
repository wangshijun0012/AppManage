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
    private static int pageSize = 4;
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
    @Override
    public Page<AppInfo> getTheList(HttpSession session, HttpServletRequest request){
        EntityWrapper wrapper = new EntityWrapper();
        String softwareName = request.getParameter("querySoftwareName");
        String queryStatus = request.getParameter("queryStatus");
        String queryFlatformId = request.getParameter("queryFlatformId");
        String queryCategoryLevel1 = request.getParameter("queryCategoryLevel1");
        String queryCategoryLevel2 = request.getParameter("queryCategoryLevel2");
        String queryCategoryLevel3 = request.getParameter("queryCategoryLevel3");

        if(softwareName != null && !"".equals(softwareName)){
            wrapper.like("softwareName", softwareName);
            request.setAttribute("softwareName", softwareName);
        }
        if(queryStatus != null && !"".equals(queryStatus)){
            wrapper.eq("status", queryStatus);
            request.setAttribute("queryStatus", queryStatus);
        }
        if(queryFlatformId != null && !"".equals(queryFlatformId)){
            wrapper.eq("flatformId",queryFlatformId);
            request.setAttribute("queryFlatformId", queryFlatformId);
        }
        if(queryCategoryLevel1 != null && !"".equals(queryCategoryLevel1)){
            wrapper.eq("categoryLevel1", queryCategoryLevel1);
            request.setAttribute("queryCategoryLevel1", queryCategoryLevel1);
        }
        if(queryCategoryLevel2 != null && !"".equals(queryCategoryLevel2)){
            wrapper.eq("categoryLevel2", queryCategoryLevel2);
            request.setAttribute("queryCategoryLevel2", queryCategoryLevel2);
        }
        if(queryCategoryLevel3 != null & !"".equals(queryCategoryLevel3)){
            wrapper.eq("categoryLevel3", queryCategoryLevel3);
            request.setAttribute("queryCategoryLevel3", queryCategoryLevel3);
        }
        String currentPageNoStr = request.getParameter("pageIndex");

        Integer currentPageNo = null;
        if(currentPageNoStr == null){
             currentPageNo = 1;
        }else{
             currentPageNo = Integer.parseInt(currentPageNoStr);
        }
        Page<AppInfo> page = new Page<AppInfo>(currentPageNo,pageSize);
        List<AppInfo> appInfos = appInfoMapper.MutilSelectList(page,wrapper);
        page.setRecords(appInfos);

        System.out.println(page);
        return page;
    }


}
