package com.bov.mt.constant;

public class URLString {
    //根据账号密码获取token
    public static final String UAA_TOKEN_URL = "http://199.224.20.214:8080/oauth/token";
    //获取某个用户的信息
    public static final String UAA_ONEUSER_URL = "http://199.224.20.214:8081/api/users/";
    //注册用户(无需token值)
    public static final String UAA_REGISTERUSER = "http://199.224.20.214:8081/api/outerRegister/finish";
    //获取手机验证码(无需token值)
    public static final String UAA_GETCODE = "http://199.224.20.214:8081/api/outerRegister/init";
    //绑定个人实名认证
    public static final String UAA_CERTIFICATION = "http://199.224.20.214:8081/api/account/realName";
    //个人实名认证信息
    public static final String UAA_ONEUSERVERIFY = "http://199.224.20.214:8081/api/account/realName/";
    //绑定企业认证
    public static final String UAA_BINDCOMPANY = "http://199.224.20.214:8081/api/account/bindEnterprise";//绑定企业
    //根据用户名获取已经绑定的企业信息
    public static final String UAA_GETBINDCOMPANYINFO = "http://199.224.20.214:8081/api/account/bindEnterprise?login.equals=";
    //获取绑定企业的营业执照
    public static final String UAA_COMPANYLICENSEPHOTO = "http://199.224.20.214:8081/api/account/bindEnterprise/";

//    private static final String UAA_MODIFYPHONE = "http://199.224.20.214:8081/api/account/resetPhone";
//    private static final String UAA_CHANGEPWD = "http://199.224.20.214:8081/api/account/change-password";
//    private static final String UAA_GETBINDCOMPANYINFOBYCODE = "http://199.224.20.214:8081/api/account/bindEnterprise?";
}
