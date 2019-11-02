package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.dto.ModuleDto;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.ModuleQuery;
import com.mage.crm.service.ModuleService;
import com.mage.crm.vo.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@RequestMapping("module")
@Controller
public class ModuleController extends BaseController {
    @Autowired
    private ModuleService moduleService;

    @RequestMapping("index")
    public String index(){
        return "module";
    }

    @RequestMapping("queryAllsModuleDtos")
    @ResponseBody
    public List<ModuleDto> queryAllsModuleDtos(Integer rid){
        return moduleService.queryAllsModuleDtos(rid);
    }

    //模块管理查询
    @RequestMapping("queryModulesByParams")
    @ResponseBody
    public Map<String,Object> queryModulesByParams(ModuleQuery moduleQuery){
        return moduleService.queryModulesByParams(moduleQuery);
    }

    @RequestMapping("queryModulesByGrade")
    @ResponseBody
    public List<Module> queryModulesByGrade(Integer grade){
        return moduleService.queryModulesByGrade(grade);
    }

    @RequestMapping ("insert")
    @ResponseBody
    public MessageModel insert(Module module) {
        MessageModel messageModel = new MessageModel();
        moduleService.insert(module);
        messageModel.setMsg("添加模块成功");
        return messageModel;
    }
}
