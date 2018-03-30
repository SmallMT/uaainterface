package com.bov.mt.services;

import com.bov.mt.Interceptor.BasicAuthInterceptor;
import com.bov.mt.constant.AdminInfo;
import com.bov.mt.constant.OAuth2Client;
import com.bov.mt.constant.URLString;
import com.bov.mt.entity.OAuthHeader;
import com.bov.mt.entity.Token;
import com.bov.mt.entity.User;
import com.bov.mt.entity.result.*;
import com.bov.mt.entity.vm.CertificateCompanyVM;
import com.bov.mt.entity.vm.CertificateUserVM;
import com.bov.mt.entity.vm.RegisterUserVM;
import com.bov.mt.utils.PostMan;
import net.sf.json.JSONObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.net.www.http.HttpClient;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component("interfaceService")
public class InterfaceService {

    Logger logger = LoggerFactory.getLogger(InterfaceService.class);

    @Value("${file.temp-path}")
    private String filePath;

    @Autowired
    private PostMan postMan;

    //根据用户名密码获取token值
    public Token getTokenByUsernameAndPwd(String username, String password){
        Token token = new Token();
        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "password")
                .add("username",username)
                .add("password",password)
                .build();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor(OAuth2Client.USER.getValue(),OAuth2Client.PASSWORD.getValue())).build();
        Request request = new Request.Builder()
                .url(URLString.UAA_TOKEN_URL)//
                .post(formBody)//
                .addHeader("Accept", "application/json")//
                .addHeader("Authorization","Basic dGVzdDoxMjM=")//
                .build();
        try {
            Response response = client.newCall(request).execute();
            int code = response.code();//获取返回状态码
            String responseBody = response.body().string();
            switch (code) {
                case 200 :
                    JSONObject json = JSONObject.fromObject(responseBody);
                    token.setCode(200);
                    token.setToken(json.getString("access_token"));
                    token.setError("");
                    break;
                case 401 :
                    token.setCode(401);
                    token.setToken("");
                    token.setError("获取token值时失败，401->未授权");
                    break;
                case 400 :
                    token.setCode(400);
                    token.setToken("");
                    token.setError("获取token值时失败，400->参数错误");
                    break;
                case 500 :
                    token.setCode(500);
                    token.setToken("");
                    token.setError("获取token值时失败，500->访问远程服务失败");
                    break;
                default :
                    break;
            }
        }catch (Exception e) {
            logger.debug("==========获取token值时请求失败============");
            logger.debug("==========错误信息============"+e.getMessage());
        }
        return token;
    }

    public CheckUsernameResult usernameIsExist(String username){
        CheckUsernameResult result = new CheckUsernameResult();
        //使用管理员获取token值
        Token token = getTokenByUsernameAndPwd(AdminInfo.ADMINNAME,AdminInfo.ADMINPWD);
        if (token.getCode() != 200) {
            //获取token值失败
            logger.debug("=========使用管理员身份获取token值失败================");
            logger.debug("=============="+token.getError()+"===========");
            result.setCode(400);
            result.setDetail(token.getError());
            return result;
        }

        return result;
    }

    //获取短信验证码
    public SendPhoneResult getTelCode(String tel){
        SendPhoneResult result = null;
        OAuthHeader headers = new OAuthHeader();
        headers.setContentType("application/json");
        JSONObject params = new JSONObject();
        params.put("phone",tel);
        Optional<HttpClientResult> clientResult = postMan.postMethod(URLString.UAA_GETCODE,headers,params);
        if (clientResult.get().getCode() == 200) {
            //获取成功
            result = new SendPhoneResult();
            result.setStatus(200);
            result.setDetail("发送成功,注意查收");
        }else {
            result = (SendPhoneResult) JSONObject.toBean(JSONObject.fromObject(clientResult.get().getContent()),SendPhoneResult.class);
        }
        return result;
    }

    //注册用户
    public RegisterResult registerUser(JSONObject vm){
        RegisterResult registerResult = null;
        OAuthHeader headers = new OAuthHeader();
        headers.setContentType("application/json");
        Optional<HttpClientResult> optional = postMan.postMethod(URLString.UAA_REGISTERUSER, headers,vm);
        HttpClientResult httpClientResult = optional.get();
        if (httpClientResult.getCode() != 200){
            //注册失败
            registerResult = (RegisterResult) JSONObject.toBean(JSONObject.fromObject(httpClientResult.getContent()),RegisterResult.class);
        }else {
            registerResult = new RegisterResult();
            registerResult.setType("no problem");
            registerResult.setStatus(200);
            registerResult.setTitle("register success");
            registerResult.setViolations(null);
        }
        return registerResult;

    }

    //用户实名认证
    public CertificateUserResult certificateUser(CertificateUserVM vm){
        CertificateUserResult result = null;
        //响应头
        OAuthHeader headers = new OAuthHeader();
        headers.setAuthorization(vm.getToken());
        //普通字段
        Map<String,String> texts = new HashMap<>();
        texts.put("login",vm.getLogin());
        texts.put("identity",vm.getIdentity());
        texts.put("name",vm.getName());
        texts.put("state",vm.getState());
        //文件字段
        File front = new File(filePath+vm.getFrontFile().getOriginalFilename());
        File back = new File(filePath+vm.getBackFile().getOriginalFilename());
        try {
            vm.getFrontFile().transferTo(front);
            vm.getBackFile().transferTo(back);
        }catch (IOException e) {
            logger.debug("=========写入临时文件失败==========");
            logger.debug("=========="+e.getMessage()+"==========");
        }
        Map<String,File> files = new HashMap<>();
        files.put("frontFile",front);
        files.put("backFile",back);
        Optional<HttpClientResult> optional = postMan.postWithFile(URLString.UAA_CERTIFICATION, headers, texts, files);
        HttpClientResult httpClientResult = optional.get();
        if (httpClientResult.getCode() != 201) {
            //认证失败
            result = (CertificateUserResult) JSONObject.toBean(JSONObject.fromObject(httpClientResult.getContent()),CertificateUserResult.class);
        }else {
            result = new CertificateUserResult();
            result.setStatus(200);
            result.setTitle("OK");
            result.setMessage("certification is successful");
        }
        //删除临时文件
        front.delete();
        back.delete();
        return result;
    }

    //企业绑定
    public CertificateCompanyResult certificateCompany(CertificateCompanyVM vm){
        CertificateCompanyResult result = null;
        OAuthHeader headers = new OAuthHeader();
        headers.setAuthorization(vm.getToken());
        Map<String,String> texts = new HashMap<>();
        texts.put("login",vm.getLogin());
        texts.put("creditCode",vm.getCreditCode());
        texts.put("enterpriseName",vm.getEnterpriseName());
        texts.put("enterpriseAddress",vm.getEnterpriseAddress());
        texts.put("legalPersonID",vm.getLegalPersonID());
        texts.put("legalPersonName",vm.getLegalPersonName());
        texts.put("legalPersonPhone",vm.getLegalPersonPhone());
        texts.put("state","通过");
        File yyzz = new File(filePath+vm.getBusinessLicenseFile().getOriginalFilename());
        try {
            vm.getBusinessLicenseFile().transferTo(yyzz);
        }catch (IOException e) {
            logger.debug("========上传企业执照,写入临时文件失败==============");
            logger.debug("==============="+e.getMessage()+"=====================");
        }
        Map<String,File> files = new HashMap<>();
        files.put("businessLicenseFile",yyzz);
        Optional<HttpClientResult> optional = postMan.postWithFile(URLString.UAA_BINDCOMPANY, headers, texts, files);
        HttpClientResult httpClientResult = optional.get();
        if (httpClientResult.getCode() != 201) {
            //企业认证失败
            result = (CertificateCompanyResult) JSONObject.toBean(JSONObject.fromObject(httpClientResult.getContent()),CertificateCompanyResult.class);
        } else {
            result = new CertificateCompanyResult();
            result.setStatus(200);
            result.setTitle("OK");
            result.setMessage("binding company successful");
        }
        return result;
    }
    //获取用户信息
    public Optional<GetUserResult> getUserInfo(String token ,String username){
        GetUserResult userResult = new GetUserResult();
        String url = URLString.UAA_ONEUSER_URL + username;
        OAuthHeader headers = new OAuthHeader();
        headers.setAccept("application/json");
        headers.setAuthorization(token);
        Optional<HttpClientResult> optional = postMan.getMethod(url,headers);
        HttpClientResult result = optional.get();
        if (result.getCode() != 200) {
            //获取用户信息失败
            logger.debug("======获取用户信息失败==========");
            userResult.setCode(result.getCode());
            userResult.setUser(null);
        }else {
            //解析optional
            User user = (User) JSONObject.toBean(JSONObject.fromObject(result.getContent()),User.class);
            userResult.setCode(200);
            userResult.setUser(user);
        }
        return Optional.of(userResult);
    }

    //获取绑定企业信息
    public JSONObject bindingCompanyInfo(String token,String username){
        OAuthHeader headers = new OAuthHeader();
        headers.setAuthorization(token);
        String url = URLString.UAA_GETBINDCOMPANYINFO+username;
        Optional<HttpClientResult> optional = postMan.getMethod(url, headers);
        HttpClientResult httpClientResult = optional.get();
        return JSONObject.fromObject(httpClientResult.getContent());
    }

    //获取绑定企业营业执照
    public byte[] companyImage(String token,String imageURL){
        return postMan.getImage(token,imageURL);
    }
}
