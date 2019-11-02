package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CustomerDao;
import com.mage.crm.dao.CustomerLossDao;
import com.mage.crm.dto.CustomerDto;
import com.mage.crm.query.CustomerGCQuery;
import com.mage.crm.query.CustomerQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.Customer;
import com.mage.crm.vo.CustomerLoss;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {
    @Resource
    private CustomerDao customerDao;
    @Resource
    private CustomerLossDao customerLossDao;

    public List<Customer> queryAllCustomers(){
        List<Customer> customers = customerDao.queryAllCustomers();
        return customers;
    }

    public Map<String,Object> queryCustomersByParams(CustomerQuery customerQuery) {
        PageHelper.startPage(customerQuery.getPage(),customerQuery.getRows());
        List<Customer> customerList = customerDao.queryCustomersByParams(customerQuery);
        PageInfo<Customer> customerPageInfo = new PageInfo<>(customerList);
        List<Customer> list = customerPageInfo.getList();
        Map<String, Object> map = new HashMap<>();
        map.put("rows",list);
        map.put("total",customerPageInfo.getTotal());
        return map;
    }

    public void insert(Customer customer) {
        //检查不能为空的参数
        chackParams(customer.getName(),customer.getFr(),customer.getPhone());
        //是否流失0为流失
        customer.setState(0);
        customer.setIsValid(1);
        customer.setKhno("KN"+new SimpleDateFormat("yyyyMMdd").format(new Date()));
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());
        int i = customerDao.insert(customer);
        AssertUtil.isTrue(i<1,"添加用户失败！");

    }

    private void chackParams(String name, String fr, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(name),"请输入客户名称！");
        AssertUtil.isTrue(StringUtils.isBlank(fr),"请输入法人！");
        AssertUtil.isTrue(StringUtils.isBlank(phone),"请输入电话号码！");
    }

    public void update(Customer customer) {
        //检查不能为空的参数
        chackParams(customer.getName(),customer.getFr(),customer.getPhone());
        //设置修改时间
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(customerDao.update(customer)<1,"修改失败");
    }

    public void delete(Integer[] id) {
        AssertUtil.isTrue(customerDao.delete(id)<1,"删除失败");
    }


    //计时器
    public void updateCustomerLossState() {
        List<CustomerLoss> customerLossList = customerDao.queryCustomerLoss();
        if(customerLossList!=null){
            for(CustomerLoss customerLoss:customerLossList){
                customerLoss.setIsValid(1);
                customerLoss.setCreateDate(new Date());
                customerLoss.setUpdateDate(new Date());
                customerLoss.setState(0);//预处理流失 暂缓流失
            }
        }
        //将查出来已经丢失的用户添加到customerLoss表中
        AssertUtil.isTrue( customerLossDao.insertBatch(customerLossList)<1,"客户流失数据添加失败");


    }

    public Map<String, Object> queryCustomersContribution(CustomerGCQuery customerGCQuery) {
        PageHelper.startPage(customerGCQuery.getPage(),customerGCQuery.getRows());
        List<CustomerDto> customerDtos = customerDao.queryCustomersContribution(customerGCQuery);
        PageInfo<CustomerDto> customerDtoPageInfo = new PageInfo<>(customerDtos);
        Map<String, Object> map = new HashMap<>();
        map.put("total",customerDtoPageInfo.getTotal());
        map.put("rows",customerDtoPageInfo.getList());
        return map;

    }

    public Map<String, Object> queryCustomerGC() {
        List<CustomerDto>  customerDtos = customerDao.queryCustomerGC();

        /**
         * 准备 X坐标的数组 和 Y坐标的数组
         *
         */
        String[] levels = null;

        Integer[] counts = null;

        Map<String,Object> map  = new HashMap<>();

        map.put("code",300);

        if(customerDtos!=null&&customerDtos.size()>0){

            levels=new String[customerDtos.size()];
            counts=new Integer[customerDtos.size()];
            for (int i = 0; i <customerDtos.size() ; i++) {
                CustomerDto customerDto  = customerDtos.get(i);
                levels[i] = customerDto.getLevel();
                counts[i] = customerDto.getCount();
            }
            map.put("code",200);
        }
        map.put("levels",levels);
        map.put("counts",counts);
        return map;
    }
}

