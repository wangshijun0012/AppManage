package cn.onesdream.service;

import cn.onesdream.dao.AppCategoryMapper;
import cn.onesdream.pojo.AppCategory;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
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

}
