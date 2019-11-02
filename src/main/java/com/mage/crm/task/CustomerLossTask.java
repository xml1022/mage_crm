package com.mage.crm.task;

import com.mage.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CustomerLossTask {
    @Autowired
    private CustomerService customerService;

    /**
     * 定时器
     */
//    @Scheduled(cron = "0 04 10 * * ?")//秒 分 时 天 月  星期
//    public void customerLoss(){
//        customerService.updateCustomerLossState();
//    }
}
