package com.woniukeji.jianmerchant.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.RegionBean;
import com.woniukeji.jianmerchant.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private BaseBean<List<RegionBean>> dataSet;
    private Context context;
    boolean isChecked = false;

    public RecyclerAdapter(BaseBean<List<RegionBean>> dataSet, Context context) {

        this.dataSet = dataSet;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_create_publish, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_select.setText(dataSet.getData().get(position).getRegion());
        if (dataSet.getData().get(position).isSelect()) {
            holder.tv_select.setTextColor(Color.parseColor("#ffffff"));
            holder.tv_select.setBackgroundColor(Color.parseColor("#3c9cff"));
        } else {
            holder.tv_select.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.tv_select.setTextColor(Color.parseColor("#666666"));
        }
        holder.tv_select.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if (isChecked) {
                    holder.tv_select.setTextColor(Color.parseColor("#666666"));
                    holder.tv_select.setBackgroundColor(Color.parseColor("#ffffff"));
                    dataSet.getData().get(position).setSelect(false);
                    isChecked = false;
                } else {
                    holder.tv_select.setTextColor(Color.parseColor("#ffffff"));
                    holder.tv_select.setBackgroundColor(Color.parseColor("#3c9cff"));
                    dataSet.getData().get(position).setSelect(true);
                    isChecked = true;
                }


//                if (!dataSet.getData().get(0).isSelect()) {
//                    if (position != 0) {
//                        if (!dataSet.getData().get(position).isSelect()) {
//                            holder.tv_select.setTextColor(Color.parseColor("#666666"));
//                            holder.tv_select.setBackgroundColor(Color.parseColor("#ffffff"));
//                        }
//                    }
//                }
                if (dataSet.getData().get(0).isSelect()) {
                    for (int i = 1; i < dataSet.getData().size(); i++) {
                        dataSet.getData().get(i).setSelect(false);
                    }

                    notifyDataSetChanged();
                }

                    LogUtils.i("postion",position+"");
//                    if (!dataSet.getData().get(position).isSelect()) {//没有被选择
//                        holder.tv_select.setTextColor(Color.parseColor("#ffffff"));
//                        holder.tv_select.setBackgroundColor(Color.parseColor("#3c9cff"));
//                        dataSet.getData().get(position).setSelect(true);
//                    } else if (dataSet.getData().get(position).isSelect()) {
//                        holder.tv_select.setTextColor(Color.parseColor("#666666"));
//                        holder.tv_select.setBackgroundColor(Color.parseColor("#ffffff"));
//                        dataSet.getData().get(position).setSelect(false);
//                    }
//
//                if (position == 0) {//第一个全国
//                    if (!dataSet.getData().get(0).isSelect()) {//从没有选择到选择后的操作
//                        for (int i = 1; i < dataSet.getData().size(); i++) {
//                            dataSet.getData().get(i).setSelect(false);
//                        }
//                        holder.tv_select.setTextColor(Color.parseColor("#ffffff"));
//                        holder.tv_select.setBackgroundColor(Color.parseColor("#3c9cff"));
//                        dataSet.getData().get(0).setSelect(true);
//                    }else if (dataSet.getData().get(0).isSelect()){//选择到没有选择状态
//                        holder.tv_select.setTextColor(Color.parseColor("#666666"));
//                        holder.tv_select.setBackgroundColor(Color.parseColor("#ffffff"));
//                        dataSet.getData().get(0).setSelect(false);
//                        notifyDataSetChanged();
//                    }
//
//                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.getData().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_select;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_select = (TextView) itemView.findViewById(R.id.tv_select);
        }
    }
}
