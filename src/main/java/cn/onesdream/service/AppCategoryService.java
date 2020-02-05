package cn.onesdream.service;

import cn.onesdream.pojo.AppCategory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface AppCategoryService {
    List<AppCategory> getLevel1();
    List<AppCategory> getLevelByParent(String pid);
    List<AppCategory> getLevel2();
    List<AppCategory> getLevel3();
    String getLevel1Name(Long level1);
    String getLevel2Name(Long level2);
    String getLevel3Name(Long level3);

}
