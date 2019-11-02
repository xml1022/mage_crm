package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.SaleChanceDao;
import com.mage.crm.query.SaleChanceQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class SaleChanceService {
    @Resource
    private SaleChanceDao saleChanceDao;

    public Map<String, Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery){
        PageHelper.startPage(saleChanceQuery.getPage(),saleChanceQuery.getRows());
        List<SaleChance> saleChances = saleChanceDao.querySaleChancesByParams(saleChanceQuery);
        PageInfo<SaleChance> saleChancePageInfo = new PageInfo<>(saleChances);
        List<SaleChance> list = saleChancePageInfo.getList();
        System.out.println(list);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows",list);//传给前台的list集合
        map.put("total",saleChancePageInfo.getTotal());
        System.out.println(map);
        return map;
    }

    /**
     * 添加营销机会
     * @param saleChance
     */
    public void insert(SaleChance saleChance) {
        //检查不可为空项
        checkParams(saleChance.customerName, saleChance.linkMan, saleChance.linkPhone);
        //设置创建时间
        saleChance.setCreateDate(new Date());
        //设置修改时间
        saleChance.setUpdateDate(new Date());
        //设置是否有效，有效才会显示在数据表中
        saleChance.setIsValid(1);
        //设置是否已分配 0位未分配
        saleChance.setState(0);
        //开发情况
        saleChance.setDevResult(0);
        if(!StringUtils.isBlank(saleChance.assignMan)){
            saleChance.setState(1);
        }
        AssertUtil.isTrue(saleChanceDao.insert(saleChance)<1,"添加营销机会失败！");
    }

    //检查不可为空项
    private void checkParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"客户名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"联系人不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"联系电话不能为空！");

    }

    /**
     * 修改营销机会
     * @param saleChance
     */
    public void update(SaleChance saleChance) {
        //检查不可为空项
        checkParams(saleChance.customerName, saleChance.linkMan, saleChance.linkPhone);
        //设置修改时间
        saleChance.setUpdateDate(new Date());
        //设置是否已分配 0位未分配
        saleChance.setState(0);
        //开发情况
        saleChance.setDevResult(0);
        if(!StringUtils.isBlank(saleChance.assignMan)){
            saleChance.setState(1);
            saleChance.setAssignTime(new Date());
        }
        AssertUtil.isTrue(saleChanceDao.update(saleChance)<1,"修改营销机会失败！");
    }

    public void delete(Integer[] id) {
        AssertUtil.isTrue(saleChanceDao.delete(id)<1,"删除营销机会失败！");
    }

    public SaleChance querySaleChanceById(String id) {
        return saleChanceDao.querySaleChanceById(id);
    }

    /**
     * 开发计划中的根据salachance查询salechance
     * @param saleChance
     * @return
     */
    public SaleChance querySaleChancesById(Integer saleChance) {
        return saleChanceDao.querySaleChancesById(saleChance);
    }

    public void updateSaleChanceDevResult(Integer devResult, Integer saleChanceId) {
        saleChanceDao.updateSaleChanceDevResult(devResult, saleChanceId);
    }
}
