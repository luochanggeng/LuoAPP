package com.luo.app.tvDemo.dataSource;

import com.luo.sdk.network.resultBean.ContentList;
import com.luo.sdk.network.resultBean.FolderInfo;

/**
 * desc :
 * Created by luo
 * on 2023/3/2
 */
public interface IDataSource {

    String queryPassWord();

    FolderInfo queryFolderInfo();

    ContentList queryContentList(String folderCode);

}
