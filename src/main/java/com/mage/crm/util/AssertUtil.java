package com.mage.crm.util;

import com.mage.crm.base.exceptions.ParamsException;

/**
 * 用来进行判断
 */
public class AssertUtil {
    public static void isTrue(boolean flag,String msg){
        if(flag){
            throw new ParamsException(300,msg);
        }

    }
    public static void isTure(boolean flag,Integer code,String msg){
        if(flag){
            throw new ParamsException(code,msg);
        }
    }
}
