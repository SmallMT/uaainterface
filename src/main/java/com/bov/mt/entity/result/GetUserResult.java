package com.bov.mt.entity.result;

import com.bov.mt.entity.User;

public class GetUserResult {

    private int code;
    private User user;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
