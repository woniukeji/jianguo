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
import com.woniukeji.jianmerchant.entity.CityAndCategoryBean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/22.
 */
public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {
    private BaseBean<List<CityAndCategoryBean.ListTTypeBean>> dataSet;
    private Context context;


    public JobsAdapter(BaseBean<List<CityAndCategoryBean.ListTTypeBean>> dataSet, Context context) {
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
        holder.tv_select.setText(dataSet.getData().get(position).getType_name());
        if (dataSet.getData().get(position).isSelect()) {
            holder.tv_select.setTextColor(context.getResources().getColor(R.color.tab_selected_text));
            holder.tv_select.setBackgroundColor(context.getResources().getColor(R.color.tab_selected_bg));
        } else {
            holder.tv_select.setBackgroundColor(context.getResources().getColor(R.color.tab_unselected_bg));
            holder.tv_select.setTextColor(context.getResources().getColor(R.color.tab_unselected_text));
        }
        holder.tv_select.setOnClickListener(new View.OnClickListener() {

            boolean isChecked = false;
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                holder.tv_select.setTextColor(Color.parseColor("#ffffff"));
                holder.tv_select.setBackgroundColor(Color.parseColor("#3c9cff"));
                dataSet.getData().get(position).setSelect(true);
                if (position == 0) {
                    if (dataSet.getData().get(0).isSelect()) {
                        for (int i = 1; i < dataSet.getData().size(); i++) {
                            dataSet.getData().get(i).setSelect(false);
                        }
                        notifyDataSetChanged();
                    }
                }
                if (position != 0) {
                    if (dataSet.getData().get(position).isSelect()) {
                        for (int i=0;i<position;i++) {
                            dataSet.getData().get(i).setSelect(false);
                        }
                        for (int i=dataSet.getData().size()-1;i>position;i--) {
                            dataSet.getData().get(i).setSelect(false);
                        }
                    }
                    notifyDataSetChanged();
                }



            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_select;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_select = (TextView) itemView.findViewById(R.id.tv_select);
        }
    }
}
