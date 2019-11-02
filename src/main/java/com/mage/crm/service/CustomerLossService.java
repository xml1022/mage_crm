package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CustomerLossDao;
import com.mage.crm.query.CustomerLossQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.CustomerLoss;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerLossService {
    @Autowired
    private CustomerLossDao customerLossDao;

    public Map<String, Object> queryCustomerLossesByParams(CustomerLossQuery customerLossQuery) {
        PageHelper.startPage(customerLossQuery.getPage(),customerLossQuery.getRows());
        List<CustomerLoss> customerLosseList = customerLossDao.queryCustomerLossesByParams(customerLossQuery);
        PageInfo<CustomerLoss> customerLossPageInfo = new PageInfo<>(customerLosseList);
        Map<String, Object> Map = new HashMap<>();
        Map.put("totals",customerLossPageInfo.getTotal());
        Map.put("rows",customerLossPageInfo.getList());
        return Map;
    }


    public Map queryCustomerLossById(Integer lossId) {
        Map stringObjectMap = customerLossDao.queryCustomerLossById(lossId);
        return stringObjectMap;
    }

    public void updateCustomerLossState(Integer lossId, String lossReason) {
        AssertUtil.isTrue(StringUtils.isBlank(lossReason),"流失原因不能为空");
        Map<String,Object> map =queryCustomerLossById(lossId);
        AssertUtil.isTrue(map==null||map.isEmpty(),"流失记录不存在");
        AssertUtil.isTrue(customerLossDao.updateCustomerLossState(lossId,lossReason) <1,"操作失败");
    }
}
