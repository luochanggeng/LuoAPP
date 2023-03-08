package com.luo.app.network.resultBean;

/**
 * desc :
 * create by 公子赓
 * on 2023/3/7 19:51
 */
public class ContentDetail {

    private String contentId ;

    private String contentName ;

    private String image ;

    private String label ;

    private String desc ;

    private String playUrl ;

    public ContentDetail() {}

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    @Override
    public String toString() {
        return "ContentDetail{" +
                "contentId='" + contentId + '\'' +
                ", contentName='" + contentName + '\'' +
                ", image='" + image + '\'' +
                ", label='" + label + '\'' +
                ", desc='" + desc + '\'' +
                ", playUrl='" + playUrl + '\'' +
                '}';
    }
}
