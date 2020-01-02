package cn.onesdream.dao;

import cn.onesdream.pojo.DataDictionary;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DataDictionaryMapper  extends BaseMapper<DataDictionary> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(DataDictionary record);

    DataDictionary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DataDictionary record);

    int updateByPrimaryKey(DataDictionary record);
}