package cn.onesdream.dao;

import cn.onesdream.pojo.AdPromotion;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AdPromotionMapper extends BaseMapper<AdPromotion> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(AdPromotion record);

    AdPromotion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdPromotion record);

    int updateByPrimaryKey(AdPromotion record);
}