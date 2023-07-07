package com.luo.app.tvDemo.list;

import com.luo.app.base.IBasePresenter;
import com.luo.sdk.network.resultBean.ContentDetail;

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
