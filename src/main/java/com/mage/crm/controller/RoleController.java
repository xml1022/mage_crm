package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.RoleQuery;
import com.mage.crm.service.RoleService;
import com.mage.crm.vo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("index")
    public String index(){
        return "role";
    }


    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Role> queryAllRoles(){
       return roleService.queryAllRoles();
    }

    @RequestMapping("queryRolesByParams")
    @ResponseBody
    public Map<String,Object> queryRolesByParams(RoleQuery roleQuery){
        return roleService.queryRolesByParams(roleQuery);
    }

    @RequestMapping("insert")
    @ResponseBody
    public MessageModel insert(Role role){
        MessageModel messageModel = new MessageModel();
        roleService.insert(role);
        messageModel.setMsg("添加角色成功");
        return messageModel;
    }


    @RequestMapping("update")
    @ResponseBody
    public MessageModel update(Role role){
        MessageModel messageModel = new MessageModel();
        roleService.update(role);
        messageModel.setMsg("修改角色成功");
        return messageModel;
    }
    @RequestMapping("delete")
    @ResponseBody
    public MessageModel delete(Integer id){
        MessageModel messageModel = new MessageModel();
        roleService.delete(id);
        messageModel.setMsg("删除角色成功");
        return messageModel;
    }

    //关联权限

    @RequestMapping("addPermission")
    @ResponseBody
    public MessageModel addPermission(Integer rid,Integer[] moduleIds){
        MessageModel messageModel = new MessageModel();
        roleService.addPermission(rid,moduleIds);
        messageModel.setMsg("角色授权成功");
        return messageModel;
    }



}
