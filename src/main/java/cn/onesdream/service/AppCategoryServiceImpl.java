package cn.onesdream.service;

import cn.onesdream.dao.AppCategoryMapper;
import cn.onesdream.pojo.AppCategory;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class AppCategoryServiceImpl implements AppCategoryService {

    @Resource
    private AppCategoryMapper appCategoryMapper;
    @Override
    public List<AppCategory> getLevel1() {

            EntityWrapper<AppCategory> wrapper = new EntityWrapper<>();
            wrapper.isNull("parentId");
            List<AppCategory> categories = appCategoryMapper.selectList(wrapper);
            return categories;

    }

    @Override
    public List<AppCategory> getLevelByParent(String pid) {
        EntityWrapper<AppCategory> wrapper = new EntityWrapper<>();
        wrapper.eq("parentId",pid);
        List<AppCategory> categories = appCategoryMapper.selectList(wrapper);
        return categories;
    }

    @Override
    public List<AppCategory> getLevel2() {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.in("parentId", Arrays.asList(1,2));
        List selectList = appCategoryMapper.selectList(wrapper);
        return selectList;
    }

    @Override
    public List<AppCategory> getLevel3() {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.isNotNull("parentId");
        wrapper.notIn("parentId", Arrays.asList(1,2));
        List selectList = appCategoryMapper.selectList(wrapper);
        return selectList;
    }

    @Override
    public String getLevel1Name(Long level1) {
        AppCategory appCategory = appCategoryMapper.selectById(level1);
        return appCategory.getCategoryName();
    }

    @Override
    public String getLevel2Name(Long level2) {
        AppCategory appCategory = appCategoryMapper.selectById(level2);
        return appCategory.getCategoryName();
    }

    @Override
    public String getLevel3Name(Long level3) {
        AppCategory appCategory = appCategoryMapper.selectById(level3);
        return appCategory.getCategoryName();
    }

}
