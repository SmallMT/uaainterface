package com.bov.mt.entity.vm;

import org.springframework.web.multipart.MultipartFile;

public class CertificateCompanyVM {
    private String token;
    private String login;
    private String enterpriseName;
    private String creditCode;
    private String enterpriseAddress;
    private String legalPersonID;
    private String legalPersonName;
    private String state;
    private String legalPersonPhone;
    private MultipartFile businessLicenseFile;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getEnterpriseAddress() {
        return enterpriseAddress;
    }

    public void setEnterpriseAddress(String enterpriseAddress) {
        this.enterpriseAddress = enterpriseAddress;
    }

    public String getLegalPersonID() {
        return legalPersonID;
    }

    public void setLegalPersonID(String legalPersonID) {
        this.legalPersonID = legalPersonID;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLegalPersonPhone() {
        return legalPersonPhone;
    }

    public void setLegalPersonPhone(String legalPersonPhone) {
        this.legalPersonPhone = legalPersonPhone;
    }

    public MultipartFile getBusinessLicenseFile() {
        return businessLicenseFile;
    }

    public void setBusinessLicenseFile(MultipartFile businessLicenseFile) {
        this.businessLicenseFile = businessLicenseFile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
