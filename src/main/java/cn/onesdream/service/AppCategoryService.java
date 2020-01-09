package cn.onesdream.service;

import cn.onesdream.pojo.AppCategory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface AppCategoryService {
    List<AppCategory> getLevel1(HttpServletRequest request);
    List<AppCategory> getLevelByParent(String pid);
    List<AppCategory> getLevel2(HttpServletRequest request);
    List<AppCategory> getLevel3();


}
