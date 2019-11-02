package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("report")
public class ReportController extends BaseController {
    @Autowired
    private ReportService reportService;

    @RequestMapping("/{type}")
    private String report(@PathVariable("type") Integer type){
        if(type==0){
            return "customer_contribution";
        }else if(type==1){
            return "customer_gc";
        }else if(type==2){
            return "customer_serve";
        }else if(type==3){
            return "customer_loss";
        }else{
            return "error";
        }
    }
}
