package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.CustomerLossQuery;
import com.mage.crm.service.CustomerLossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequestMapping("customer_loss")
@Controller
public class CustomerLossController extends BaseController {
    @Autowired
    private CustomerLossService customerLossService;
    //跳转页面
    @RequestMapping("index")
    public String index(){
        return "customer_loss";
    }

    @RequestMapping("queryCustomerLossesByParams")
    @ResponseBody
    public Map<String, Object> queryCustomerLossesByParams(CustomerLossQuery customerLossQuery) {
        return customerLossService.queryCustomerLossesByParams(customerLossQuery);
    }

    @RequestMapping("toRepreivePage/{lossId}")
    public String toRepreivePage(@PathVariable("lossId") Integer lossId,Model model){
        model.addAttribute("customerLoss", customerLossService.queryCustomerLossById(lossId));
        return "customer_repri";
    }

    @RequestMapping("updateCustomerLossState")
    @ResponseBody
    public MessageModel updateCustomerLossState(Integer lossId, String lossReason){
        MessageModel messageModel = new MessageModel();
        customerLossService.updateCustomerLossState(lossId,lossReason);
        messageModel.setMsg("确认流失成功！");
        return messageModel;
    }

}
