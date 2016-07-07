package com.haibin.qiaqia.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haibin.qiaqia.R;
import com.haibin.qiaqia.entity.ListMarket;
import com.haibin.qiaqia.listener.MyItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cai on 2016/6/25.
 */

public class MarketClassAdapter extends RecyclerView.Adapter<MarketClassAdapter.ViewHolder> {


    private Context context;
    private List<ListMarket> list;
    private MyItemClickListener myItemClickListener;
    public MarketClassAdapter(Context context, List<ListMarket> list,MyItemClickListener myItemClickListener) {
        this.context = context;
        this.list = list;
        this.myItemClickListener=myItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_market_class, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ListMarket listMarket = list.get(position);
        holder.itemMarketName.setText(listMarket.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myItemClickListener.onItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        System.out.print("list :" + list.size());
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_market_name)
        TextView itemMarketName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
