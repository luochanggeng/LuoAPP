package com.luo.app.tvDemo.login;

import android.text.TextUtils;

import com.luo.app.tvDemo.dataSource.DataSource;
import com.luo.app.tvDemo.dataSource.IDataSource;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * desc :
 * Created by luo
 * on 2023/3/2
 */
public class LoginPresenter implements LoginContract.ISplashPresenter {

    private LoginContract.ISplashView mView ;

    private IDataSource dataSource ;

    public LoginPresenter(LoginContract.ISplashView view) {
        this.mView = view;
        dataSource = new DataSource();
    }

    @Override
    public void releaseView() {
        mView = null;
        dataSource = null;
    }

    @Override
    public void queryPassword() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) {
                if(dataSource != null){
                    String s = dataSource.queryPassWord();
                    if(!TextUtils.isEmpty(s)){
                        emitter.onNext(s);
                    }
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}

            @Override
            public void onComplete() {}

            @Override
            public void onError(@NonNull Throwable e) {
                if (mView != null) {
                    mView.showPassword("");
                }
            }

            @Override
            public void onNext(@NonNull String password) {
                if (mView != null) {
                    mView.showPassword(password);
                }
            }
        });
    }
}
