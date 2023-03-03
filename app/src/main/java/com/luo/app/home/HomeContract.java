package com.luo.app.home;

import com.luo.app.base.IBasePresenter;
import com.luo.app.network.resultBean.FolderInfo;

/**
 * desc :
 * create by 公子赓
 * on 2023/3/3 20:14
 */
public class HomeContract {

    interface IHomeView{

        void showFolderInfo(FolderInfo folderInfo);

    }

    interface IHomePresenter extends IBasePresenter {

        void queryFolderInfo();

    }

}
