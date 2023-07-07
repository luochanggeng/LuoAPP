package com.luo.app.tvDemo.login;

import com.luo.app.base.IBasePresenter;

/**
 * desc :
 * Created by luo
 * on 2023/3/2
 */
public interface LoginContract {

    interface ISplashView{

        void showPassword(String password);

    }

    interface ISplashPresenter extends IBasePresenter {

        void queryPassword();

    }
}
