package com.luo.app.list;

import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luo.app.R;
import com.luo.app.base.BaseActivity;
import com.luo.app.list.adapter.FolderListAdapter;
import com.luo.app.network.resultBean.ContentDetail;

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

    private FolderListAdapter adapter;

    @Override
    protected void initIntent() {
        folderCode = getIntent().getStringExtra("folderCode");
        folderName = getIntent().getStringExtra("folderName");
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_list);

        TextView tvFolderName = findViewById(R.id.tv_folder_name);
        tvFolderName.setText(folderName);

        RecyclerView rvFolderList = findViewById(R.id.rv_folder_list);
        rvFolderList.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new FolderListAdapter(this, null);
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
}
