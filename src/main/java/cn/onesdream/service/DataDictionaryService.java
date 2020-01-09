package cn.onesdream.service;

import cn.onesdream.pojo.DataDictionary;

import java.util.List;

public interface DataDictionaryService {
    List<DataDictionary> getAllStatus();
    List<DataDictionary> getAllFlatForm();
    List<DataDictionary> getDataDictionaryList(String typeCode);
}
