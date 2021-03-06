package cn.onesdream.service;

import cn.onesdream.dao.DataDictionaryMapper;
import cn.onesdream.pojo.DataDictionary;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
@Transactional
public class DataDictionaryServiceImpl implements DataDictionaryService {
    @Resource
    private DataDictionaryMapper dataDictionaryMapper;
    @Override
    public List<DataDictionary> getAllStatus() {
        EntityWrapper<DataDictionary> wrapper = new EntityWrapper<>();
        wrapper.eq("typeCode","APP_STATUS");
        List<DataDictionary> dictionaries = dataDictionaryMapper.selectList(wrapper);
        return dictionaries;
    }

    @Override
    public List<DataDictionary> getAllFlatForm() {
        EntityWrapper<DataDictionary> wrapper = new EntityWrapper<>();
        wrapper.eq("typeCode", "APP_FLATFORM");
        List<DataDictionary> dictionaries = dataDictionaryMapper.selectList(wrapper);
        return dictionaries;
    }

    @Override
    public String getFlatFormNameById(Long flatFormId) {
        DataDictionary dataDictionary = new DataDictionary();
        dataDictionary.setTypeCode("APP_FLATFORM");
        dataDictionary.setValueId(flatFormId);
        DataDictionary dictionary = dataDictionaryMapper.selectOne(dataDictionary);
        return dictionary.getValueName();
    }
}
