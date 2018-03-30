package com.bov.mt.entity.vm;

import org.springframework.web.multipart.MultipartFile;

public class CertificateUserVM {

    private String token;
    private String login;
    private String identity;
    private String name;
    private String state;
    private MultipartFile frontFile;
    private MultipartFile backFile;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public MultipartFile getFrontFile() {
        return frontFile;
    }

    public void setFrontFile(MultipartFile frontFile) {
        this.frontFile = frontFile;
    }

    public MultipartFile getBackFile() {
        return backFile;
    }

    public void setBackFile(MultipartFile backFile) {
        this.backFile = backFile;
    }
}
