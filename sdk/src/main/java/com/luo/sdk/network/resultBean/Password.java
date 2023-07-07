package com.luo.sdk.network.resultBean;

/**
 * desc :
 * create by 公子赓
 * on 2023/3/3 20:00
 */
public class Password {

    private String password ;

    public Password() {}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Password{" +
                "password='" + password + '\'' +
                '}';
    }
}
