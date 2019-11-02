package com.mage.crm.interceptors;

import com.mage.crm.service.UserService;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.util.Base64Util;
import com.mage.crm.util.CookieUtil;
import com.mage.crm.util.CrmConstant;
import com.mage.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从cookie中拿到id
        String userId = CookieUtil.getCookieValue(request, "userId");
        //判断id是否为空StringUtils.isBlank(userId),判断参数
        AssertUtil.isTure(StringUtils.isBlank(userId),CrmConstant.LOGIN_FAILED_CODE,CrmConstant.LOGIN_FAILED_MSG);
        //对id进行解密
        String id = Base64Util.deCode(userId);
        //查询id是否存在
        User user = userService.queryUserById(id);
        //判断user是否存在null==user，判断对象
        AssertUtil.isTure(null==user,CrmConstant.LOGIN_EXITS_CODE,CrmConstant.LOGIN_EXITS_MSG);
        //判断用户的状态
        AssertUtil.isTure("0".equals(user.getIsValid()),CrmConstant.LOGIN_FAILED_CODE,CrmConstant.LOGIN_FAILED_MSG);
        return true;
    }
}
