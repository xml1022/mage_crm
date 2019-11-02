package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CusDevPlanDao;
import com.mage.crm.dao.SaleChanceDao;
import com.mage.crm.query.CustomerDevPlanQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.CustomerDevPlan;
import com.mage.crm.vo.SaleChance;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CusDevPlanService {
    @Resource
    private CusDevPlanDao cusDevPlanDao;
    @Resource
    private SaleChanceDao saleChanceDao;

    public Map<String,Object> queryCusDevPlans(CustomerDevPlanQuery customerDevPlanQuery) {
        PageHelper.startPage(customerDevPlanQuery.getPage(),customerDevPlanQuery.getRows());
        List<CustomerDevPlan> customerDevPlanList=cusDevPlanDao.queryCusDevPlans(customerDevPlanQuery.getSaleChanceId());
        PageInfo<CustomerDevPlan> customerDevPlanPageInfo = new PageInfo<>(customerDevPlanList);
        Map<String, Object> map = new HashMap<>();
        map.put("total",customerDevPlanPageInfo.getTotal());
        map.put("rows",customerDevPlanPageInfo.getList());
        return map;
    }

    /**
     *查看详情中的添加，添加营销机会
     * @param customerDevPlan
     */
    public void insert(CustomerDevPlan customerDevPlan) {
        //先到营销机会表中查询
        SaleChance saleChance = saleChanceDao.querySaleChancesById(customerDevPlan.getSaleChanceId());
        AssertUtil.isTrue(saleChance==null,"营销机会不能为空！");
        AssertUtil.isTrue(saleChance.getDevResult()==2,"营销机会已经完成");
        AssertUtil.isTrue(saleChance.getDevResult()==3,"营销机会失败");
        customerDevPlan.setIsValid(1);
        customerDevPlan.setCreateDate(new Date());
        customerDevPlan.setUpdateDate(new Date());
        //将查到的营销机会插入到客户开发计划表中
        AssertUtil.isTrue(cusDevPlanDao.insert(customerDevPlan)<1,"添加失败");
    }

    public void update(CustomerDevPlan customerDevPlan) {
        SaleChance saleChance = saleChanceDao.querySaleChancesById(customerDevPlan.getSaleChanceId());
        AssertUtil.isTrue(saleChance==null,"营销机会不能为空！");
        AssertUtil.isTrue(saleChance.getDevResult()==2,"营销机会已经完成");
        AssertUtil.isTrue(saleChance.getDevResult()==3,"营销机会失败");
        customerDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanDao.update(customerDevPlan)<1,"添加失败");
    }

    public void delete(Integer id) {
        AssertUtil.isTrue(cusDevPlanDao.delete(id)<1,"删除失败");
    }
}
