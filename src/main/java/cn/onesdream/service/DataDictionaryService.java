package cn.onesdream.service;

import cn.onesdream.pojo.DataDictionary;

import java.util.List;

public interface DataDictionaryService {
    List<DataDictionary> getAllStatus();//查询APP状态
    List<DataDictionary> getAllFlatForm();//查询APP平台
    List<DataDictionary> getDataDictionaryList(String typeCode);
}
