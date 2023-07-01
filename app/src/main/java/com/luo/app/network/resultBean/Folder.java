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

    private String folderTitle ;

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

    public String getFolderTitle() {
        return folderTitle;
    }

    public void setFolderTitle(String folderTitle) {
        this.folderTitle = folderTitle;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "folderCode='" + folderCode + '\'' +
                ", folderName='" + folderName + '\'' +
                ", folderImage='" + folderImage + '\'' +
                ", folderTitle='" + folderTitle + '\'' +
                '}';
    }
}
