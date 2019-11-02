package com.mage.crm.controller;

import com.mage.crm.model.MessageModel;
import com.mage.crm.query.CustomerRepriQuery;
import com.mage.crm.service.CustomerRepriService;
import com.mage.crm.vo.CustomerReprieve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequestMapping("customer_repri")
@Controller
public class CustomerRepriController {
    @Autowired
    private CustomerRepriService customerRepriService;

    @RequestMapping("customerReprieveByLossId")
    @ResponseBody
    public Map<String,Object> customerReprieveByLossId(CustomerRepriQuery customerRepriQuery){
        Map<String, Object> stringObjectMap = customerRepriService.customerReprieveByLossId(customerRepriQuery);
        return stringObjectMap;
    }


    @RequestMapping("insertReprive")
    @ResponseBody
    public MessageModel insertReprive(CustomerReprieve customerReprieve){
        MessageModel messageModel = new MessageModel();
        customerRepriService.insert(customerReprieve);
        messageModel.setMsg("添加成功");
        return messageModel;
    }

    @RequestMapping("updateReprive")
    @ResponseBody
    public MessageModel updateReprive(CustomerReprieve customerReprieve){
        MessageModel messageModel = new MessageModel();
        customerRepriService.update(customerReprieve);
        messageModel.setMsg("修改成功");
        return messageModel;
    }
    @RequestMapping("delete")
    @ResponseBody
    public MessageModel delete(Integer id){
        MessageModel messageModel = new MessageModel();
        customerRepriService.delete(id);
        messageModel.setMsg("删除成功");
        return messageModel;
    }
}
