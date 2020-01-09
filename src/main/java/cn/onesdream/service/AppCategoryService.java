package cn.onesdream.service;

import cn.onesdream.pojo.AppCategory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * app分类    业务层
 */
public interface AppCategoryService {
    List<AppCategory> getAppCategoryListByParentId(Integer parentId);

    List<AppCategory> getLevelByParent(String pid);
    List<AppCategory> getLevel1();
    List<AppCategory> getLevel2();
    List<AppCategory> getLevel3();
}
