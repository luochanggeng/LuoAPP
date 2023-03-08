package com.luo.app.dataSource;

import com.luo.app.network.resultBean.ContentList;
import com.luo.app.network.resultBean.FolderInfo;

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
