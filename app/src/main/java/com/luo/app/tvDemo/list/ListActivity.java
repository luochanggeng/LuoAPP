package com.luo.app.tvDemo.list;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.luo.app.R;
import com.luo.app.base.BaseActivity;
import com.luo.app.tvDemo.list.adapter.FolderListAdapter;
import com.luo.sdk.network.resultBean.ContentDetail;
import com.luo.app.tvDemo.utils.ImageLoader;
import com.luo.app.tvDemo.widget.MyGridLayoutManager;
import com.luo.app.tvDemo.widget.MyItemDecoration;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * desc :
 * Created by luo
 * on 2023/5/24
 */
public class ListActivity extends BaseActivity implements ListContract.IListView {

    ListContract.IListPresenter presenter ;

    private String folderCode;
    private String folderName;
    private String folderTitle;

    private FolderListAdapter adapter;

    @Override
    protected void initIntent() {
        folderCode = getIntent().getStringExtra("folderCode");
        folderTitle = getIntent().getStringExtra("folderTitle");
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_list);

        ImageView tvFolderName = findViewById(R.id.iv_folder_title);
        ImageLoader.loadImage(this, folderTitle, tvFolderName, R.color.trans);

        RecyclerView rvFolderList = findViewById(R.id.rv_folder_list);
        rvFolderList.setHasFixedSize(true);
        //加大RecyclerView的缓存 用空间换时间，来提高滚动的流畅性。
        rvFolderList.setItemViewCacheSize(20);
        rvFolderList.setDrawingCacheEnabled(true);
        rvFolderList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rvFolderList.setItemAnimator(null);
        rvFolderList.addItemDecoration(new MyItemDecoration((int) getResources().getDimension(R.dimen.px_80), (int) getResources().getDimension(R.dimen.px_40)));
        rvFolderList.setLayoutManager(new MyGridLayoutManager(this, 2));
        rvFolderList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //恢复Glide加载图片
                    Glide.with(ListActivity.this).resumeRequests();
                } else {
                    //禁止Glide加载图片
                    Glide.with(ListActivity.this).pauseRequests();
                }
            }
        });

        adapter = new FolderListAdapter(this, null);
        adapter.setHasStableIds(true);
        rvFolderList.setAdapter(adapter);

        presenter = new ListPresenter(this);
        presenter.queryContentList(folderCode);
    }

    @Override
    public void showContentList(List<ContentDetail> contentDetailList) {
        if(contentDetailList == null || contentDetailList.isEmpty()){
            return;
        }
        adapter.setContentList(contentDetailList);
    }

    private long preKeyDown = 0L ;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN){
                long curKeyDown = System.currentTimeMillis();
                if(curKeyDown - preKeyDown < 100){
                    return true;
                }
                preKeyDown = curKeyDown;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
