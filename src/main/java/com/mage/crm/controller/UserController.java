package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.base.exceptions.ParamsException;
import com.mage.crm.model.MessageModel;
import com.mage.crm.model.UserModel;
import com.mage.crm.query.UserQuery;
import com.mage.crm.service.UserService;
import com.mage.crm.util.CookieUtil;
import com.mage.crm.util.CrmConstant;
import com.mage.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    //打开用户信息管理
    @RequestMapping("index")
    public String index(){
        return "user";
    }

    //登录操作,前台传过来的userName,userPwd
    @RequestMapping("userLogin")
    @ResponseBody
    public MessageModel userLogin(String userName, String userPwd){
        MessageModel messageModel = new MessageModel();
        try{
            //将密码和用户名传给userService进行逻辑判断，并得到返回结果
            UserModel userModel = userService.userLogin(userName, userPwd);//有可能抛出异常user==null
            messageModel.setResult(userModel);
        } catch (ParamsException px){
            px.printStackTrace();
            messageModel.setCode(CrmConstant.LOGIN_FAILED_CODE);
            messageModel.setMsg(px.getMsg());
        }catch (Exception ex){
            ex.printStackTrace();
            messageModel.setCode(CrmConstant.OPS_FAILED_CODE);
            messageModel.setMsg(CrmConstant.OPS_FAILED_MSG);
        }
        return messageModel;
    }


    //修改密码
    @RequestMapping("updatePwd")
    @ResponseBody
    public MessageModel updatePwd(HttpServletRequest request,String oldPassword,String newPassword,String confirmPassword){
        MessageModel messageModel = new MessageModel();
        //从cookie中获取id（加密过后的id）
        String userId = CookieUtil.getCookieValue(request,"userId");

        try{
            //将密码和用户名传给userService进行逻辑判断，并得到返回结果
            userService.updatePwd(userId,oldPassword,newPassword,confirmPassword);
            messageModel.setMsg("修改密码成功！");
        } catch (ParamsException px){
            px.printStackTrace();
            messageModel.setCode(CrmConstant.LOGIN_FAILED_CODE);
            messageModel.setMsg(px.getMsg());
        }catch (Exception ex){
            ex.printStackTrace();
            messageModel.setCode(CrmConstant.OPS_FAILED_CODE);
            messageModel.setMsg(CrmConstant.OPS_FAILED_MSG);
        }
        return messageModel;
    }

    /**
     * 获取营销机会管理选项卡添加对话框中的分配人信息
     * @return
     */
    @RequestMapping("queryAllCustomerManager")
    @ResponseBody
    public List<User> queryAllCustomerManager(){
        List<User> users = userService.queryAllCustomerManager();
        return users;
    }

    @RequestMapping("queryUsersByParams")
    @ResponseBody
    public Map<String,Object> queryUsersByParams(UserQuery userQuery){
        Map<String, Object> map = userService.queryUsersByParams(userQuery);
        return map;
    }

    @RequestMapping("insert")
    @ResponseBody
    public MessageModel insert(User user){
        MessageModel messageModel = new MessageModel();
        userService.insert(user);
        messageModel.setMsg("添加用户成功");
        return messageModel;
    }
    @RequestMapping("update")
    @ResponseBody
    public MessageModel update(User user){
        MessageModel messageModel = new MessageModel();
        userService.update(user);
        messageModel.setMsg("添加用户成功");
        return messageModel;
    }
    @RequestMapping("delete")
    @ResponseBody
    public MessageModel delete(Integer id){
        MessageModel messageModel = new MessageModel();
        userService.delete(id);
        messageModel.setMsg("添加用户成功");
        return messageModel;
    }


}
