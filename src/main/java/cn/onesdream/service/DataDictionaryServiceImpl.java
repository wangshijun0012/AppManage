package cn.onesdream.service;

import cn.onesdream.dao.DataDictionaryMapper;
import cn.onesdream.pojo.DataDictionary;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {
    @Resource
    private DataDictionaryMapper dataDictionaryMapper;
    //查询APP状态
    @Override
    public List<DataDictionary> getAllStatus() {
        EntityWrapper<DataDictionary> wrapper = new EntityWrapper<>();
        wrapper.eq("typeCode", "APP_STATUS");
        List<DataDictionary> dictionaries = dataDictionaryMapper.selectList(wrapper);
        return dictionaries;
    }
    //查询APP平台
    @Override
    public List<DataDictionary> getAllFlatForm() {
        EntityWrapper<DataDictionary> wrapper = new EntityWrapper<>();
        wrapper.eq("typeCode", "APP_FLATFORM");//条件 typeCode为APP_FLTFORM
        List<DataDictionary> dictionaries = dataDictionaryMapper.selectList(wrapper);
        return dictionaries;
    }
    //查询符合typeCode的DataDictonary
    @Override
    public List<DataDictionary> getDataDictionaryList(String typeCode) {
        EntityWrapper<DataDictionary> wrapper = new EntityWrapper<>();
        wrapper.eq("typeCode",typeCode);
        List<DataDictionary> dataDictionaries=dataDictionaryMapper.selectList(wrapper);
        return dataDictionaries;
    }
}
