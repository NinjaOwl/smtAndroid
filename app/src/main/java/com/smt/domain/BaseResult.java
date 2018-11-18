package com.smt.domain;

public class BaseResult {
    public String code = "";
    public String msg = "";

    @Override
    public String toString() {
        return "BaseResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
