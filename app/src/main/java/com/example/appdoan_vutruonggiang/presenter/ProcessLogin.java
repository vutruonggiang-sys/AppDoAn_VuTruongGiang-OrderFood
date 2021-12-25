package com.example.appdoan_vutruonggiang.presenter;


import com.example.appdoan_vutruonggiang.inteface.ILogin;

public class ProcessLogin {
    ILogin iLogin;

    public ProcessLogin(ILogin iLogin) {
        this.iLogin = iLogin;
    }
    public void onLogin(int d){
        if(d==1){
            iLogin.loginSuccessful("Bạn đăng nhập thành công");
        }
        if(d==0){
            iLogin.loginError("Kiểm tra lại thông tin tài khoản");
        }
    }
}
