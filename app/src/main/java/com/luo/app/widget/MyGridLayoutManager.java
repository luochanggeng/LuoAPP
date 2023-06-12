package com.luo.app.widget;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import org.jetbrains.annotations.NotNull;

/**
 * desc : 长按焦点控制
 * create by 公子赓
 * on 2023/2/21 20:08
 */
public class MyGridLayoutManager extends GridLayoutManager {

    private int mSpanCount ;

    public MyGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
        this.mSpanCount = spanCount ;
    }

    @Override
    public View onInterceptFocusSearch(@NotNull View focused, int direction) {
        int position;
        int spanCount = getSpanCount();
        int itemCount = getItemCount();
        try {
            position = getPosition(focused);
        }catch (Exception e){
            e.printStackTrace();
            return super .onInterceptFocusSearch(focused, direction);
        }
        if (direction == View.FOCUS_DOWN) {
            int lastVisibleItemPosition = findLastVisibleItemPosition();
            if(position > lastVisibleItemPosition){
                return focused;
            }else {
                position += spanCount;
                if (position >= itemCount){
                    position = itemCount - 1;
                }
                View nextView = findViewByPosition(position);
                if (nextView == null){
//                    if (lastRowAbleFocusView(getPosition(focused))){
                        return super.onInterceptFocusSearch(focused, direction);
////                    }else {
//                        return focused;
////                    }
                }else {
                    if ((!nextView.willNotDraw() || nextView.isDirty())){
                        return focused;
                    }
                    return nextView;
                }
            }
        }else {
            int firstVisibleItemPosition = findFirstVisibleItemPosition();
            if(position < firstVisibleItemPosition){
                return focused;
            }
        }
        return super.onInterceptFocusSearch(focused, direction);
    }
}
