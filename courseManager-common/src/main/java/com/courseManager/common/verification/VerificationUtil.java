package com.courseManager.common.verification;

import java.util.regex.Pattern;

/**
 *  正则表达式 数据校验工具类
 */
public class VerificationUtil {

    //身份证校验
    public static boolean idCardVail(String idCard){
        String regex = "(\\d{17}[0-9a-zA-Z]|\\d{14}[0-9a-zA-Z])";
        return Pattern.matches(regex,idCard);
    }

    //手机号校验
    public static boolean phoneNumberVail(String phoneNumber){
        String regex = "(^1[3-9]\\d{9}$)";
        return Pattern.matches(regex,phoneNumber);
    }

}
