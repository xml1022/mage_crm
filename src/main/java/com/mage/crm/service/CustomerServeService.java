package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CustomerServeDao;
import com.mage.crm.query.CustomerServeQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.util.CookieUtil;
import com.mage.crm.vo.CustomerServe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServeService {
    @Autowired
    private CustomerServeDao customerServeDao;


    public void insert(CustomerServe customerServe) {
        checkParams(customerServe.getServeType(),customerServe.getServiceRequest(),customerServe.getCustomer(),customerServe.getOverview());
        customerServe.setCreateDate(new Date());
        customerServe.setUpdateDate(new Date());
        customerServe.setIsValid(1);
        customerServe.setState("1");
        AssertUtil.isTrue(customerServeDao.insert(customerServe)<1,"服务创建失败");
    }

    private void checkParams(String serveType, String serviceRequest, String customer, String overview) {
        AssertUtil.isTrue(StringUtils.isBlank(serveType),"请选择服务类型！");
        AssertUtil.isTrue(StringUtils.isBlank(serviceRequest),"请选择服务类型！");
        AssertUtil.isTrue(StringUtils.isBlank(customer),"请选择服务类型！");
        AssertUtil.isTrue(StringUtils.isBlank(overview),"请选择服务类型！");

    }

    public Map<String,Object> queryCustomerServesByParams(CustomerServeQuery customerServeQuery) {
        PageHelper.startPage(customerServeQuery.getPage(),customerServeQuery.getRows());
        List<CustomerServe> customerServes = customerServeDao.queryCustomerServesByParams(customerServeQuery.getState());
        PageInfo<CustomerServe> customerServePageInfo = new PageInfo<>(customerServes);
        Map<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("total",customerServePageInfo.getTotal());
        stringObjectHashMap.put("rows",customerServePageInfo.getList());
        return stringObjectHashMap;
    }
    public void update(CustomerServe customerServe , HttpServletRequest request){
        customerServe.setUpdateDate(new Date());
        if(customerServe.getState().equals("2")){
            customerServe.setAssigner(CookieUtil.getCookieValue(request,"trueName"));
            customerServe.setAssignTime(new Date());
        }else if(customerServe.getState().equals("3")){
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProce()),"处理内容不能为空");
            customerServe.setServiceProceTime(new Date());
        }else if(customerServe.getState().equals("4")) {
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProceResult()),"处理结果不能为空");
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getMyd()),"满意度不能为空");
            customerServe.setState("5");
        }
        AssertUtil.isTrue(customerServeDao.update(customerServe)<1,"操作失败");
    }
}
