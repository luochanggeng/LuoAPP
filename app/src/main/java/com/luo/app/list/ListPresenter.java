package com.luo.app.list;

import androidx.annotation.NonNull;

import com.luo.app.dataSource.DataSource;
import com.luo.app.dataSource.IDataSource;
import com.luo.app.home.HomeContract;
import com.luo.app.network.resultBean.ContentList;
import com.luo.app.network.resultBean.FolderInfo;

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
 * on 2023/5/24
 */
public class ListPresenter implements ListContract.IListPresenter {

    private ListContract.IListView mView;

    private IDataSource dataSource;

    public ListPresenter(ListContract.IListView view) {
        this.mView = view;
        dataSource = new DataSource();
    }


    @Override
    public void queryContentList(String folderCode) {
        Observable.create(new ObservableOnSubscribe<ContentList>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<ContentList> emitter) {
                if (dataSource != null) {
                    ContentList contentList = dataSource.queryContentList(folderCode);
                    if (contentList != null) {
                        emitter.onNext(contentList);
                    }
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ContentList>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}
            @Override
            public void onComplete() {}
            @Override
            public void onError(@NonNull Throwable e) {}
            @Override
            public void onNext(@NonNull ContentList contentList) {
                if (mView != null) {
                    mView.showContentList(contentList.getContentList());
                }
            }
        });
    }

    @Override
    public void releaseView() {
        mView = null;
        dataSource = null;
    }
}
