package com.luo.app.list;

import com.luo.app.base.IBasePresenter;
import com.luo.app.network.resultBean.ContentDetail;
import com.luo.app.network.resultBean.FolderInfo;

import java.util.List;

/**
 * desc :
 * Created by luo
 * on 2023/5/24
 */
public class ListContract {

    interface IListView{

        void showContentList(List<ContentDetail> contentDetailList);

    }

    interface IListPresenter extends IBasePresenter {

        void queryContentList(String folderCode);

    }

}
