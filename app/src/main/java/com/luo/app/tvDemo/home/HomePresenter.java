package com.luo.app.tvDemo.home;

import androidx.annotation.NonNull;

import com.luo.app.tvDemo.dataSource.DataSource;
import com.luo.app.tvDemo.dataSource.IDataSource;
import com.luo.sdk.network.resultBean.ContentDetail;
import com.luo.sdk.network.resultBean.ContentList;
import com.luo.sdk.network.resultBean.Folder;
import com.luo.sdk.network.resultBean.FolderInfo;

import java.util.List;

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

    private HomeContract.IHomeView mView;

    private IDataSource dataSource;

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
                if (dataSource != null) {
                    FolderInfo folderInfo = dataSource.queryFolderInfo();
                    if (folderInfo != null) {
                        emitter.onNext(folderInfo);
                    }
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<FolderInfo>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }

            @Override
            public void onNext(@NonNull FolderInfo folderInfo) {
                if (mView != null) {
                    mView.showFolderInfo(folderInfo);
                }
            }
        });
    }

    @Override
    public void querySmallWindowVideo() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) {
                if (dataSource != null) {
                    FolderInfo folderInfo = dataSource.queryFolderInfo();
                    if (folderInfo == null) {
                        return;
                    }
                    List<Folder> folderList = folderInfo.getFolderList();
                    if (folderList == null || folderList.isEmpty()) {
                        return;
                    }
                    int a = (int) (Math.random() * folderList.size());
                    Folder folder = folderList.get(a);
                    if (folder == null) {
                        return;
                    }
                    ContentList contentList = dataSource.queryContentList(folder.getFolderCode());
                    if (contentList == null) {
                        return;
                    }
                    List<ContentDetail> contentDetailList = contentList.getContentList();
                    if (contentDetailList == null || contentDetailList.isEmpty()) {
                        return;
                    }
                    int b = (int) (Math.random() * contentDetailList.size());
                    ContentDetail ContentDetail = contentDetailList.get(b);
                    if (ContentDetail == null) {
                        return;
                    }
                    emitter.onNext(ContentDetail.getPlayUrl());
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}
            @Override
            public void onComplete() {}
            @Override
            public void onError(@NonNull Throwable e) {}
            @Override
            public void onNext(@NonNull String url) {
                if (mView != null) {
                    mView.showSmallWindowVideo(url);
                }
            }
        });
    }
}
