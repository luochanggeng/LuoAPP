package com.luo.sdk.network.resultBean;

import java.util.List;

/**
 * desc :
 * create by 公子赓
 * on 2023/3/7 19:48
 */
public class ContentList {

    private List<ContentDetail> contentList ;

    public ContentList() {}

    public List<ContentDetail> getContentList() {
        return contentList;
    }

    public void setContentList(List<ContentDetail> contentList) {
        this.contentList = contentList;
    }

    @Override
    public String toString() {
        return "ContentList{" +
                "contentList=" + contentList +
                '}';
    }
}
