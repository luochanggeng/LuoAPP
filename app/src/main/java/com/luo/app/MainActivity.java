package com.luo.app;

import android.content.ComponentName;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luo.app.base.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * desc : 我的APP主页
 * create by 公子赓
 * on 2023/7/7 15:00
 */
public class MainActivity extends BaseActivity {

    private List<String> data;

    @Override
    protected void initLayout() {
        initData();
        setContentView(R.layout.activity_mian);
        RecyclerView recyclerView = findViewById(R.id.list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new MyAdapter());
    }

    private void initData() {
        data = new ArrayList<>();
        data.add("TV应用入口&com.luo.app.tvDemo.login.LoginActivity");
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.main_list_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
            String dataString = data.get(position);
            String[] split;
            if(dataString.contains("&")) {
                split = dataString.split("&");
                holder.tvItem.setText(split[0]);
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvItem;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
            itemView.setOnClickListener(view1 -> {
                String dataString = data.get(getAdapterPosition());
                if(dataString.contains("&")) {
                    String[] split = dataString.split("&");
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(MainActivity.this,  split[1]));
                    Toast.makeText(MainActivity.this, split[1], Toast.LENGTH_LONG).show();
                    MainActivity.this.startActivity(intent);
                }
            });
        }
    }
}
