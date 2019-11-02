package com.mage.crm.dao;

import com.mage.crm.dto.CustomerDto;
import com.mage.crm.query.CustomerGCQuery;
import com.mage.crm.query.CustomerQuery;
import com.mage.crm.vo.Customer;
import com.mage.crm.vo.CustomerLoss;
import com.mage.crm.vo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerDao {
    /**
     * 查询所有用户
     * @return
     */
    List<Customer> queryAllCustomers();

    /**
     * 根据要查询的数据查询用户信息
     * @param customerQuery
     * @return
     */
    List<Customer> queryCustomersByParams(CustomerQuery customerQuery);

    int insert(Customer customer);

    int update(Customer customer);

    int delete( Integer[] id);

    //查找丢失的用户
    List<CustomerLoss> queryCustomerLoss();

    List<CustomerDto> queryCustomersContribution(CustomerGCQuery customerGCQuery);

    List<CustomerDto> queryCustomerGC();
}
