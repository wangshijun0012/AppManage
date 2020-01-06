package cn.onesdream.service;

import cn.onesdream.pojo.AppCategory;

import java.util.List;

public interface AppCategoryService {
    List<AppCategory> getLevelByParent(String categoryCode);

}
