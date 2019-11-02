package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.CustomerGCQuery;
import com.mage.crm.query.CustomerQuery;
import com.mage.crm.service.CustomerService;
import com.mage.crm.vo.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController {
    @Resource
    private CustomerService customerService;

    //获取营销机会管理中添加对话框中的客户信息
    @RequestMapping("queryAllCustomers")
    @ResponseBody//让查到的数据以json串的形式传给前台
    public List<Customer> queryAllCustomers(){
        List<Customer> customers = customerService.queryAllCustomers();
        return customers;
    }

    @RequestMapping("index")
    public String index(){
        return "customer";
    }

    @RequestMapping("queryCustomersByParams")
    @ResponseBody
    //查询客户信息管理信息
    public Map<String,Object> queryCustomersByParams(CustomerQuery customerQuery){
        Map<String, Object> map = customerService.queryCustomersByParams(customerQuery);
        return map;
    }

    /**
     * 客户信息管理添加
     */
    @RequestMapping("insert")
    @ResponseBody
    public MessageModel insert(Customer customer){
        MessageModel messageModel = new MessageModel();
        customerService.insert(customer);
        messageModel.setMsg("营销管理添加成功！");
        return messageModel;
    }

    @RequestMapping("update")
    @ResponseBody
    public MessageModel update(Customer customer){
        MessageModel messageModel =new MessageModel();
        customerService.update(customer);
        messageModel.setMsg("营销管理修改成功");
        return messageModel;
    }

    @RequestMapping("delete")
    @ResponseBody
    public MessageModel delete(Integer[] id){
        MessageModel messageModel =new MessageModel();
        customerService.delete(id);
        messageModel.setMsg("营销管理删除成功");
        return messageModel;
    }

    //报表贡献分析
    @RequestMapping("queryCustomersContribution")
    @ResponseBody
    public Map<String,Object> queryCustomersContribution(CustomerGCQuery customerGCQuery){
        return customerService.queryCustomersContribution(customerGCQuery);
    }

    //报表构成
    @RequestMapping("queryCustomerGC")
    @ResponseBody
    public Map<String,Object> queryCustomerGC(){
        return customerService.queryCustomerGC();

    }
}
