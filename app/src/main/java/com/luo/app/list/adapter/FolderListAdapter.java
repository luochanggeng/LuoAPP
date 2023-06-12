package com.luo.app.list.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luo.app.R;
import com.luo.app.network.resultBean.ContentDetail;
import com.luo.app.player.FullScreenPlayerActivity;
import com.luo.app.utils.AnimationUtils;
import com.luo.app.utils.ImageLoader;
import com.luo.app.widget.FocusFlashImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * desc :
 * Created by luo
 * on 2023/5/24
 */
public class FolderListAdapter extends RecyclerView.Adapter<FolderListAdapter.MyViewHolder> {

    private final Context mContext;

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
//        ContentDetail contentDetail = mContentList.get(position);
//        ImageLoader.loadImage(mContext, contentDetail.getImage(), holder.ivPlaybill, R.mipmap.default_bg);
//        holder.tvName.setText(contentDetail.getContentName());
        holder.tvName.setText("我的是的法撒撒旦" + position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mContentList == null ? 37 : mContentList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        FocusFlashImageView ivPlaybill ;

        TextView tvName ;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivPlaybill = itemView.findViewById(R.id.iv_playbill);
            tvName = itemView.findViewById(R.id.tv_name);

            itemView.setOnFocusChangeListener((v, b) -> {
                if(b){
                    ivPlaybill.startShimmer();
                    AnimationUtils.showOnFocusAnimation(v);
                    tvName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    tvName.setMarqueeRepeatLimit(-1);
                    //选中后开启跑马灯效果
                    tvName.setSelected(true);
                }else{
                    ivPlaybill.stopShimmer();
                    AnimationUtils.showLooseFocusAnimation(v);
                    tvName.setEllipsize(TextUtils.TruncateAt.END);
                    //失去焦点后取消跑马灯效果
                    tvName.setSelected(false);
                }
            });

            itemView.setOnClickListener(view -> {
                int adapterPosition = getAdapterPosition();
                if(adapterPosition < 0 || adapterPosition >= mContentList.size()){
                    return;
                }
                ContentDetail contentDetail = mContentList.get(adapterPosition);
                Intent intent = new Intent(mContext, FullScreenPlayerActivity.class);
                intent.putExtra("contentDetail", contentDetail);
                mContext.startActivity(intent);
            });
        }
    }
}
