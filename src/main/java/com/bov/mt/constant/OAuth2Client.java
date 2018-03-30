package com.bov.mt.constant;

public enum OAuth2Client {

    USER("uaainterface"),PASSWORD("uaainterface");

    private String value;

    OAuth2Client(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
