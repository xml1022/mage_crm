package com.mage.crm.base;


import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @ModelAttribute
    public void preMethod(HttpServletRequest request){
        //项目路径
        request.setAttribute("ctx",request.getContextPath());
    }
}
