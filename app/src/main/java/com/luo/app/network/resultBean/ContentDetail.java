package com.luo.app.network.resultBean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * desc :
 * create by 公子赓
 * on 2023/3/7 19:51
 */
public class ContentDetail implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.contentId);
        dest.writeString(this.contentName);
        dest.writeString(this.image);
        dest.writeString(this.label);
        dest.writeString(this.desc);
        dest.writeString(this.playUrl);
    }

    public void readFromParcel(Parcel source) {
        this.contentId = source.readString();
        this.contentName = source.readString();
        this.image = source.readString();
        this.label = source.readString();
        this.desc = source.readString();
        this.playUrl = source.readString();
    }

    protected ContentDetail(Parcel in) {
        this.contentId = in.readString();
        this.contentName = in.readString();
        this.image = in.readString();
        this.label = in.readString();
        this.desc = in.readString();
        this.playUrl = in.readString();
    }

    public static final Parcelable.Creator<ContentDetail> CREATOR = new Parcelable.Creator<ContentDetail>() {
        @Override
        public ContentDetail createFromParcel(Parcel source) {
            return new ContentDetail(source);
        }

        @Override
        public ContentDetail[] newArray(int size) {
            return new ContentDetail[size];
        }
    };
}
