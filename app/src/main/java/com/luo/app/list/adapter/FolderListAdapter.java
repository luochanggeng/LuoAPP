package com.luo.app.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luo.app.R;
import com.luo.app.network.resultBean.ContentDetail;
import com.luo.app.network.resultBean.FolderInfo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * desc :
 * Created by luo
 * on 2023/5/24
 */
public class FolderListAdapter extends RecyclerView.Adapter<FolderListAdapter.MyViewHolder> {

    private Context mContext;

    private List<ContentDetail> mContentList;

    public FolderListAdapter(Context context, List<ContentDetail> contentList){
        this.mContext = context;
        this.mContentList = contentList;
    }

    public void setContentList(List<ContentDetail> contentList){
        this.mContentList = contentList;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public FolderListAdapter.MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.folder_list_item, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FolderListAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mContentList == null ? 0 : mContentList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
