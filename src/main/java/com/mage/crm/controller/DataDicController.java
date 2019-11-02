package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.dao.DataDicDao;
import com.mage.crm.service.DataDicService;
import com.mage.crm.vo.DataDic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("data_dic")
public class DataDicController extends BaseController {

    @Autowired
    private DataDicService dataDicService;

    @RequestMapping("queryDataDicValueByDataDicName")
    @ResponseBody
    public List<DataDic> queryDataDicValueByDataDicName(String dataDicName){
        List<DataDic> dataDicList = dataDicService.queryDataDicValueByDataDicName(dataDicName);
        return dataDicList;
    }

}
