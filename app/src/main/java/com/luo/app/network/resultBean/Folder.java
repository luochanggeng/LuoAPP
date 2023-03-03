package com.luo.app.network.resultBean;

/**
 * desc :
 * create by 公子赓
 * on 2023/3/3 20:09
 */
public class Folder {

    private String folderCode ;

    private String folderName ;

    private String folderImage ;

    public String getFolderCode() {
        return folderCode;
    }

    public void setFolderCode(String folderCode) {
        this.folderCode = folderCode;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderImage() {
        return folderImage;
    }

    public void setFolderImage(String folderImage) {
        this.folderImage = folderImage;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "folderCode='" + folderCode + '\'' +
                ", folderName='" + folderName + '\'' +
                ", folderImage='" + folderImage + '\'' +
                '}';
    }
}
