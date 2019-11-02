package com.mage.crm.dao;

import com.mage.crm.vo.DataDic;

import java.util.List;

public interface DataDicDao {
    /**
     * 查询客户等级
     * @param dataDicName
     * @return
     */
    List<DataDic> queryDataDicValueByDataDicName(String dataDicName);
}
