package com.luo.app.tvDemo.dataSource;

import com.luo.sdk.network.NetWorkUtil;
import com.luo.sdk.network.resultBean.ContentList;
import com.luo.sdk.network.resultBean.FolderInfo;
import com.luo.sdk.network.resultBean.Password;

/**
 * desc :
 * Created by luo
 * on 2023/3/2
 */
public class DataSource implements IDataSource{

    @Override
    public String queryPassWord() {
        Password password = NetWorkUtil.getInstance().queryPassWord();
        if(password != null){
            return password.getPassword();
        }
        return "";
    }

    @Override
    public FolderInfo queryFolderInfo() {
        return NetWorkUtil.getInstance().queryFolderInfo();
    }

    @Override
    public ContentList queryContentList(String folderCode) {
        return NetWorkUtil.getInstance().queryContentList(folderCode);
    }
}
