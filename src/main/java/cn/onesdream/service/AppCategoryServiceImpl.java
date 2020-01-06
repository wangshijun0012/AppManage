package cn.onesdream.service;

import cn.onesdream.dao.AppCategoryMapper;
import cn.onesdream.pojo.AppCategory;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class AppCategoryServiceImpl implements AppCategoryService {

    @Resource
    private AppCategoryMapper appCategoryMapper;
    @Override
    public List<AppCategory> getLevelByParent(String categoryCode) {
        EntityWrapper<AppCategory> wrapper = new EntityWrapper<>();


        return null;
    }
}
