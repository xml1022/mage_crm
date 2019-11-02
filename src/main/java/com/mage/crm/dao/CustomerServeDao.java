package com.mage.crm.dao;

import com.mage.crm.vo.CustomerServe;

import java.util.List;

public interface CustomerServeDao {
    int insert(CustomerServe customerServe);

    List<CustomerServe> queryCustomerServesByParams(String state);

    int update(CustomerServe customerServe);
}
