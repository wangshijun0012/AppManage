package cn.onesdream.service;

import cn.onesdream.dao.AppCategoryMapper;
import cn.onesdream.pojo.AppCategory;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * app分类实现类
 */
@Service
public class AppCategoryServiceImpl implements AppCategoryService {

    @Resource
    private AppCategoryMapper appCategoryMapper;


    @Override
    public List<AppCategory> getLevelByParent(String pid) {
        EntityWrapper<AppCategory> wrapper = new EntityWrapper<>();
        wrapper.eq("parentId",pid);
        List<AppCategory> categories = appCategoryMapper.selectList(wrapper);
        return categories;
    }
    @Override
    public List<AppCategory> getLevel1() {

        EntityWrapper<AppCategory> wrapper = new EntityWrapper<>();
        wrapper.isNull("parentId");
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
    public List<AppCategory> getAppCategoryListByParentId(Integer parentId) {
        EntityWrapper<AppCategory> wrapper = new EntityWrapper<>();
        wrapper.eq("pid",parentId);
        List<AppCategory> list=appCategoryMapper.selectList(wrapper);
        return list;
    }
}
