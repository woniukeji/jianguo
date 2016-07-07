package com.haibin.qiaqia.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haibin.qiaqia.R;
import com.haibin.qiaqia.entity.ListChaoCommodity;
import com.haibin.qiaqia.widget.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cai on 2016/6/25.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {



    private Context context;
    private List<ListChaoCommodity> list;

    public HomeAdapter(Context context, List<ListChaoCommodity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_goods, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListChaoCommodity data = list.get(position);
        holder.tvGoodsName.setText(data.getName());
        Glide.with(context)
                .load(data.getImage())
                .placeholder(R.drawable.ic_loading_rotate)
                .crossFade()
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        System.out.print("list :" + list.size());
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_goods_name) TextView tvGoodsName;
        @BindView(R.id.tv_goods_price) TextView tvGoodsPrice;
        @BindView(R.id.img) ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
