package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.CustomerServeQuery;
import com.mage.crm.service.CustomerServeService;
import com.mage.crm.vo.CustomerServe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
@RequestMapping("customer_serve")
public class CustomerServeController extends BaseController {
    @Autowired
    private CustomerServeService customerServeService;

    @RequestMapping("index/{type}")
    public String index(@PathVariable("type") Integer type ){
        if(type==1){
            return "customer_serve_create";
        }else if(type==2){
            return "customer_serve_assign";
        }else if(type==3){
            return "customer_serve_proceed";
        }else if(type==4){
            return "customer_serve_feed_back";
        }else if(type==5){
            return "customer_serve_archive";
        }else{
            return "error";
        }
    }

    @RequestMapping("insert")
    @ResponseBody
    public MessageModel insert(CustomerServe customerServe){
        MessageModel messageModel = new MessageModel();
        customerServeService.insert(customerServe);
        messageModel.setMsg("创建服务成功");
        return messageModel;
    }
    @RequestMapping("queryCustomerServesByParams")
    @ResponseBody
    public Map<String,Object> queryCustomerServesByParams(CustomerServeQuery customerServeQuery){
        Map<String, Object> stringObjectMap = customerServeService.queryCustomerServesByParams(customerServeQuery);
        return stringObjectMap;
    }

    @RequestMapping("update")
    @ResponseBody
    public MessageModel update(CustomerServe customerServe, HttpServletRequest httpServletRequest){
        System.out.println(customerServe.getState());
        MessageModel messageModel = new MessageModel();
        customerServeService.update(customerServe,httpServletRequest);
        messageModel.setMsg("修改成功");
        return messageModel;
    }
}
