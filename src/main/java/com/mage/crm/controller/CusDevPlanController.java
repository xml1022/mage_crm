package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.dao.CusDevPlanDao;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.CustomerDevPlanQuery;
import com.mage.crm.service.CusDevPlanService;
import com.mage.crm.service.SaleChanceService;
import com.mage.crm.service.UserService;
import com.mage.crm.vo.CustomerDevPlan;
import com.mage.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("cus_dev_plan")
public class CusDevPlanController extends BaseController {
    @Resource
    private SaleChanceService saleChanceService;
    @Resource
    private CusDevPlanService cusDevPlanService;


    //点击查看详情根据id查询数据库中的营销机会信息，并将查到的信息添加到视图的作用域中
    @RequestMapping("index")
    public String index(String id, Model model){
        SaleChance saleChance = saleChanceService.querySaleChanceById(id);
        model.addAttribute("saleChance",saleChance);
        return "cus_dev_plan_detail";
    }

    /**
     * 查看详情中的分页查询
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("queryCusDevPlans")
    public Map<String,Object> queryCusDevPlans(CustomerDevPlanQuery customerDevPlanQuery){
        Map<String, Object> Map = cusDevPlanService.queryCusDevPlans(customerDevPlanQuery);
        return Map;
    }

    /**
     * 查看详情中的添加，添加营销机会
     * @param customerDevPlan
     * @return
     */
    @RequestMapping("insert")
    @ResponseBody
    public MessageModel insert(CustomerDevPlan customerDevPlan){
        MessageModel messageModel = new MessageModel();
        cusDevPlanService.insert(customerDevPlan);
        messageModel.setMsg("添加成功");
        return messageModel;

    }

    /**
     * 查看详情中的修改
     */
    @ResponseBody
    @RequestMapping("update")
    public MessageModel update(CustomerDevPlan customerDevPlan){
        MessageModel messageModel = new MessageModel();
        cusDevPlanService.update(customerDevPlan);
        messageModel.setMsg("开发计划修改成功");
        return messageModel;
    }

    /**
     * 查询详情中的删除
     */
    @ResponseBody
    @RequestMapping("delete")
    public MessageModel delete(Integer id){
        MessageModel messageModel = new MessageModel();
        cusDevPlanService.delete(id);
        messageModel.setMsg("开发计划删除成功");
        return messageModel;
    }
}
