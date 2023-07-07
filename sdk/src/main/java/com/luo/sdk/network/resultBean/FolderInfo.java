package com.luo.sdk.network.resultBean;

import java.util.List;

/**
 * desc :
 * create by 公子赓
 * on 2023/3/3 20:07
 */
public class FolderInfo {

    private String folderLogo ;

    private List<Folder> folderList ;

    public FolderInfo() {}

    public String getFolderLogo() {
        return folderLogo;
    }

    public void setFolderLogo(String folderLogo) {
        this.folderLogo = folderLogo;
    }

    public List<Folder> getFolderList() {
        return folderList;
    }

    public void setFolderList(List<Folder> folderList) {
        this.folderList = folderList;
    }

    @Override
    public String toString() {
        return "FolderInfo{" +
                "folderLogo='" + folderLogo + '\'' +
                ", folderList=" + folderList +
                '}';
    }
}
