package com.luo.app.tvDemo.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.jetbrains.annotations.NotNull;


/**
 * desc : RecyclerView StaggeredGridLayoutManager  GridLayoutManager布局的间距设置
 * create by 公子赓
 * on 2023/2/21 20:08
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration {

    private boolean includeEdge;
    private int leftRight;
    private int topBottom;

    public MyItemDecoration(int leftRight, int topBottom) {
        setLeftRight(leftRight);
        setTopBottom(topBottom);
    }

    /***
     * 设置四边是否要间距
     */
    public void setIncludeEdge(boolean includeEdge) {
        this.includeEdge = includeEdge;
    }

    /***
     * 设置左右两边的间距
     */
    public void setLeftRight(int leftRight) {
        this.leftRight = leftRight;
    }

    /***
     * 设置上下两边的间距
     */
    public void setTopBottom(int topBottom) {
        this.topBottom = topBottom;
    }

    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view, RecyclerView parent, @NotNull RecyclerView.State state) {
        int spanCount;
        if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) parent.getLayoutManager();
            spanCount = manager.getSpanCount();
        } else if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            spanCount = manager.getSpanCount();
        } else {
            spanCount = 1;
        }
        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;

        if (includeEdge) {
            // leftRight - column * ((1f / spanCount) * leftRight)
            outRect.left = leftRight - column * leftRight / spanCount;
            // (column + 1) * ((1f / spanCount) * leftRight)
            outRect.right = (column + 1) * leftRight / spanCount;
            // top edge
            if (position < spanCount) {
                outRect.top = topBottom;
            }
            // item bottom
            outRect.bottom = topBottom;
        } else {
            // column * ((1f / spanCount) * leftRight)
            outRect.left = column * leftRight / spanCount;
            outRect.left = column * leftRight / spanCount;
            // leftRight - (column + 1) * ((1f / spanCount) * leftRight)
            outRect.right = leftRight - (column + 1) * leftRight / spanCount;
            if (position >= spanCount) {
                // item top
                outRect.top = topBottom;
            }
        }

    }

}
