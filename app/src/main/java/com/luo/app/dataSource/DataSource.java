package com.luo.app.dataSource;

import com.luo.app.network.NetWorkUtil;
import com.luo.app.network.resultBean.FolderInfo;
import com.luo.app.network.resultBean.Password;

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
}
