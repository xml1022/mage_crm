package com.mage.crm.controller;

import com.mage.crm.RequestPermission;
import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.SaleChanceQuery;
import com.mage.crm.service.SaleChanceService;
import com.mage.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {//extends BaseController获取到访问路径

    @Resource
    private SaleChanceService saleChanceService;
    @RequestMapping("index/{id}")
    public String index(@PathVariable("id") String id){//PathVariable用于将请求URL(@RequestMapping("index/{id}"))中的模板变量映射到功能处理方法的参数上
        if("1".equals(id)){
            return "sale_chance";
        }else if("2".equals(id)){
            return "cus_dev_plan";
        }else{
            return "error";
        }
    }

    /**
     * 分页查询
     * @param saleChanceQuery
     * @return
     */
    @RequestMapping("querySaleChancesByParams")
    @ResponseBody
    public Map<String, Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery){
        Map<String, Object> map = saleChanceService.querySaleChancesByParams(saleChanceQuery);
        return map;
    }

    /**
     * 添加数据
     * @param saleChance
     * @return
     */
    @RequestMapping("insert")
    @ResponseBody
    public MessageModel insert(SaleChance saleChance){

        MessageModel messageModel = new MessageModel();
        saleChanceService.insert(saleChance);
        messageModel.setMsg("营销机会添加成功！");
        return messageModel;
    }

    /**
     * 修改数据
     * @param saleChance
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public MessageModel update(SaleChance saleChance){
        MessageModel messageModel = new MessageModel();
        saleChanceService.update(saleChance);
        messageModel.setMsg("营销机会修改成功！");
        return messageModel;
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @RequestPermission(aclVal = "")
    @RequestMapping("delete")
    @ResponseBody
    public MessageModel delete(Integer[] id){
        MessageModel messageModel = new MessageModel();
        saleChanceService.delete(id);
        messageModel.setMsg("营销机会删除成功！");
        return messageModel;
    }

    /**
     * 终止计划，撤销行
     */
    @RequestMapping("updateSaleChanceDevResult")
    @ResponseBody
    public MessageModel updateSaleChanceDevResult(Integer devResult,Integer saleChanceId){
        MessageModel messageModel = new MessageModel();
        saleChanceService.updateSaleChanceDevResult(devResult,saleChanceId);
        messageModel.setMsg("操作成功！");
        return messageModel;

    }
}
