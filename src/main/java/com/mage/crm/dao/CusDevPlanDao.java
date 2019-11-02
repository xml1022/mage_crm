package com.mage.crm.dao;

import com.mage.crm.query.CustomerDevPlanQuery;
import com.mage.crm.vo.CustomerDevPlan;

import java.util.List;

public interface CusDevPlanDao {
    List<CustomerDevPlan> queryCusDevPlans(Integer saleChanceId);

    int insert(CustomerDevPlan customerDevPlan);

    int update(CustomerDevPlan customerDevPlan);

    int delete(Integer id);
}
