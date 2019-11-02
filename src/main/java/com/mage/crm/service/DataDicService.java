package com.mage.crm.service;

import com.mage.crm.dao.DataDicDao;
import com.mage.crm.vo.DataDic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DataDicService {
    @Autowired
    private DataDicDao dataDicDao;

    public List<DataDic> queryDataDicValueByDataDicName(String dataDicName) {
        List<DataDic> dataDicList = dataDicDao.queryDataDicValueByDataDicName(dataDicName);
        return dataDicList;
    }
}
