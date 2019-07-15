package com.example.xykf.tanchukuang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xykf.tanchukuang.listener.onClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 生活动态
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private final Context context;
    private final String[] address;
    private onClickListener<String> listener;
    //1、定义一个集合，用来记录选中
    private List<Boolean> isClicks;

    public AddressAdapter(Context context, String[] address){
        this.context = context;
        this.address = address;

        //3、为集合添加值
        isClicks = new ArrayList<>();
        for(int i = 0;i<address.length;i++){
            isClicks.add(false);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.address_list_item,parent,false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.name.setText(address[position]);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    int position = holder.getLayoutPosition(); // 1
                    for(int i = 0; i <isClicks.size();i++){
                        isClicks.set(i,false);
                    }
                    isClicks.set(position,true);
                    notifyDataSetChanged();
                    listener.onClick(view,position, address[position]);
                }
            }
        });

        //6、判断改变属性
        if(isClicks.get(position)){
            holder.name.setTextColor(Color.BLUE);
            holder.selectImg.setVisibility(View.VISIBLE);
        }else{
            holder.name.setTextColor(Color.BLACK);
            holder.selectImg.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return address.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private ImageView selectImg;

        public ViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            selectImg=itemView.findViewById(R.id.selectImg);
        }
    }

    public void setOnClickListener(onClickListener<String> listener){
        this.listener = listener;
    }
}
