package com.luo.app.splash;

import com.luo.app.base.IBasePresenter;

/**
 * desc :
 * Created by luo
 * on 2023/3/2
 */
public interface SplashContract {

    interface ISplashView{

        void showPassword(String password);

    }

    interface ISplashPresenter extends IBasePresenter {

        void queryPassword();

    }
}
