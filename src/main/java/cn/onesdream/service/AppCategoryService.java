package cn.onesdream.service;

import cn.onesdream.pojo.AppCategory;

import java.util.List;

/**
 * app分类    业务层
 */
public interface AppCategoryService {
    List<AppCategory> getLevelByParent(String categoryCode);
    List<AppCategory> getAppCategoryListByParentId(Integer parentId);

}
