package com.bov.mt.controller;

import com.bov.mt.constant.AdminInfo;
import com.bov.mt.entity.Token;
import com.bov.mt.entity.result.*;
import com.bov.mt.entity.vm.CertificateCompanyVM;
import com.bov.mt.entity.vm.CertificateUserVM;
import com.bov.mt.services.InterfaceService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interface/api/")
public class InterfaceController {

    Logger logger = LoggerFactory.getLogger(InterfaceController.class);

    @Autowired
    private InterfaceService service;

    //获取token值
    @PostMapping("gettoken")
    public String getToken(@RequestBody JSONObject args){
        String username = args.getString("username");
        String password = args.getString("password");
        Token token = service.getTokenByUsernameAndPwd(username,password);
        return JSONObject.fromObject(token).toString();
    }

    //获取用户信息
    @PostMapping("userinfo")
    public String getUserInfo(@RequestBody JSONObject args){
        String token = args.getString("token");
        String username = args.getString("username");
        GetUserResult result = service.getUserInfo(token,username).get();
        if (result.getCode() == 200) {
            return JSONObject.fromObject(result.getUser()).toString();
        }else {
            return "{}";
        }
    }

    //查询用户是否已经注册(无需token值)
    @GetMapping("isexist")
    public String checkUsernameIsExist(@RequestParam("username") String username){
        CheckUsernameResult result = new CheckUsernameResult();
        Token tokens = service.getTokenByUsernameAndPwd(AdminInfo.ADMINNAME,AdminInfo.ADMINPWD);
        String token = tokens.getToken();
        GetUserResult userResult = service.getUserInfo(token,username).get();
        if (userResult.getCode() == 200) {
            result.setCode(200);
            result.setExist(true);
            result.setDetail("该账号已经存在");
        }else {
            result.setCode(200);
            result.setExist(false);
            result.setDetail("该账号不存在,可以使用");
        }
        return JSONObject.fromObject(result).toString();
    }

    //获取手机验证码(无需token值)
    @GetMapping("getcode")
    public String getTelCode(@RequestParam("tel") String tel){
        SendPhoneResult result = service.getTelCode(tel);
        return JSONObject.fromObject(result).toString();
    }

    //注册用户
    @PostMapping("register")
    public String register(@RequestBody JSONObject args){
        RegisterResult registerResult = service.registerUser(args);
        return JSONObject.fromObject(registerResult).toString();
    }

    //实名认证
    @PostMapping("certificateuser")
    public String certificateUser(CertificateUserVM vm){
        CertificateUserResult result = service.certificateUser(vm);
        return JSONObject.fromObject(result).toString();
    }

    //企业绑定
    @PostMapping("certificatecompany")
    public String certificateCompany(CertificateCompanyVM vm){
        CertificateCompanyResult result = service.certificateCompany(vm);
        return JSONObject.fromObject(result).toString();
    }

    //企业信息
    @PostMapping("companyinfo")
    public String certificateCompanyInfo(@RequestBody JSONObject args){
        String username = args.getString("username");
        String token = args.getString("token");
        return service.bindingCompanyInfo(token,username).toString();
    }

    @PostMapping("companyimage")
    public byte[] bindingCompanyImage(@RequestBody JSONObject args){
        String token = args.getString("token");
        String imageURL = args.getString("imageURL");
        return service.companyImage(token,imageURL);
    }
}
