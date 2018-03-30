package com.bov.mt.entity.result;

import java.util.List;
import java.util.Map;

public class RegisterResult {
    private String type;
    private int status;
    private List<Map<String,String>> violations;
    private String title;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Map<String, String>> getViolations() {
        return violations;
    }

    public void setViolations(List<Map<String, String>> violations) {
        this.violations = violations;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
