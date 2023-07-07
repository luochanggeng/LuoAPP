package com.luo.app.tvDemo.home;

import com.luo.app.base.IBasePresenter;
import com.luo.sdk.network.resultBean.FolderInfo;

/**
 * desc :
 * create by 公子赓
 * on 2023/3/3 20:14
 */
public class HomeContract {

    interface IHomeView{

        void showFolderInfo(FolderInfo folderInfo);

        void showSmallWindowVideo(String url);

    }

    interface IHomePresenter extends IBasePresenter {

        void queryFolderInfo();

        void querySmallWindowVideo();

    }

}
