package com.luo.app.home;

import androidx.annotation.NonNull;

import com.luo.app.dataSource.DataSource;
import com.luo.app.dataSource.IDataSource;
import com.luo.app.network.resultBean.FolderInfo;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * desc :
 * Created by luo
 * on 2023/3/2
 */
public class HomePresenter implements HomeContract.IHomePresenter {

    private HomeContract.IHomeView mView ;

    private IDataSource dataSource ;

    public HomePresenter(HomeContract.IHomeView view) {
        this.mView = view;
        dataSource = new DataSource();
    }

    @Override
    public void releaseView() {
        mView = null;
        dataSource = null;
    }

    @Override
    public void queryFolderInfo() {
        Observable.create(new ObservableOnSubscribe<FolderInfo>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<FolderInfo> emitter) {
                if(dataSource != null){
                    FolderInfo folderInfo = dataSource.queryFolderInfo();
                    if(folderInfo != null){
                        emitter.onNext(folderInfo);
                    }
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<FolderInfo>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}

            @Override
            public void onComplete() {}

            @Override
            public void onError(@NonNull Throwable e) {}

            @Override
            public void onNext(@NonNull FolderInfo folderInfo) {
                if (mView != null) {
                    mView.showFolderInfo(folderInfo);
                }
            }
        });
    }
}
