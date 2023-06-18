package com.luo.app.list.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
public class FolderListAdapter extends RecyclerView.Adapter<FolderListAdapter.MyViewHolder> implements Handler.Callback {

    private final Context mContext;

    private List<ContentDetail> mContentList;

    private final Handler mHandler ;

    public FolderListAdapter(Context context, List<ContentDetail> contentList){
        this.mContext = context;
        this.mContentList = contentList;
        mHandler = new Handler(Looper.getMainLooper(), this);
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull FolderListAdapter.MyViewHolder holder, int position) {
        ContentDetail contentDetail = mContentList.get(position);
        ImageLoader.loadImage(mContext, contentDetail.getImage(), holder.ivPlaybill, R.mipmap.default_bg);
        holder.tvName.setText(contentDetail.getContentName());

        String label = contentDetail.getLabel();
        if(TextUtils.isEmpty(label)){
            holder.llLabelArea.setVisibility(View.GONE);
        }else{
            holder.llLabelArea.setVisibility(View.VISIBLE);
            if(label.contains("/")){
                String[] split = label.split("/");
                if(split.length >= 1){
                    holder.tvLabel1.setText(split[0]);
                }
                if(split.length >= 2){
                    holder.tvLabel2.setText(split[1]);
                }
                if(split.length >= 3){
                    holder.tvLabel3.setVisibility(View.VISIBLE);
                    holder.tvLabel3.setText(split[2]);
                }else{
                    holder.tvLabel3.setVisibility(View.GONE);
                }
            }
        }
        holder.tvArea.setText("地区：" + contentDetail.getArea());
        holder.tvActor.setText("演员：" + contentDetail.getActor());
        holder.tvDesc.setText("简介：" + contentDetail.getDesc());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mContentList == null ? 0 : mContentList.size();
    }

    @Override
    public boolean handleMessage(@NonNull @NotNull Message message) {
        if(message.what == 0){
            RelativeLayout rlDetail = (RelativeLayout) message.obj;
            AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
            alphaAnimation.setDuration(200);
            rlDetail.startAnimation(alphaAnimation);
            rlDetail.setVisibility(View.VISIBLE);
        }
        return false;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        FocusFlashImageView ivPlaybill ;
        TextView tvName ;

        RelativeLayout rlDetail ;
        LinearLayout llLabelArea ;
        TextView tvLabel1 ;
        TextView tvLabel2 ;
        TextView tvLabel3 ;
        TextView tvArea ;
        TextView tvActor ;
        TextView tvDesc ;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivPlaybill = itemView.findViewById(R.id.iv_playbill);
            tvName = itemView.findViewById(R.id.tv_name);

            rlDetail = itemView.findViewById(R.id.rl_detail);
            llLabelArea = itemView.findViewById(R.id.ll_label_area);
            tvLabel1 = itemView.findViewById(R.id.tv_label1);
            tvLabel2 = itemView.findViewById(R.id.tv_label2);
            tvLabel3 = itemView.findViewById(R.id.tv_label3);
            tvArea = itemView.findViewById(R.id.tv_area);
            tvActor = itemView.findViewById(R.id.tv_actor);
            tvDesc = itemView.findViewById(R.id.tv_desc);

            itemView.setOnFocusChangeListener((v, b) -> {
                if(b){
                    ivPlaybill.startShimmer();
                    AnimationUtils.showOnFocusAnimation(v);
                    tvName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    tvName.setMarqueeRepeatLimit(-1);
                    //选中后开启跑马灯效果
                    tvName.setSelected(true);

                    mHandler.sendMessageDelayed(Message.obtain(mHandler, 0, rlDetail), 1500);
                }else{
                    ivPlaybill.stopShimmer();
                    AnimationUtils.showLooseFocusAnimation(v);
                    tvName.setEllipsize(TextUtils.TruncateAt.END);
                    //失去焦点后取消跑马灯效果
                    tvName.setSelected(false);

                    mHandler.removeMessages(0);
                    rlDetail.clearAnimation();
                    rlDetail.setVisibility(View.GONE);
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
