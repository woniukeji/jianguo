package com.haibin.qiaqia.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haibin.qiaqia.R;
import com.haibin.qiaqia.entity.ListChaoCommodity;
import com.haibin.qiaqia.home.HomeAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<ListChaoCommodity> list;
    private HomeAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, ListChaoCommodity data);
    }

    public CartAdapter(Context context, List<ListChaoCommodity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_cart, null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    //注意这里使用getTag方法获取数据
//                    mOnItemClickListener.onItemClick(v, (ListChaoCommodity) v.getTag());
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ListChaoCommodity data = list.get(position);
        holder.tvItemName.setText(data.getName());
        holder.tvColorName.setText(data.getAlias());
        holder.tvItemPrice.setText(String.valueOf(data.getPrice())+ "元");
        holder.tvItemCount.setText(String.valueOf(data.getCount()));
        Glide.with(context)
                .load(data.getImage())
                .placeholder(R.drawable.ic_loading_rotate)
                .crossFade()
                .into(holder.ivItemPic);
//        holder.itemView.setTag(data);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setmOnItemClickListener(HomeAdapter.OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_pic)
        ImageView ivItemPic;
        @BindView(R.id.tv_item_name)
        TextView tvItemName;
        @BindView(R.id.tv_item_color)
        TextView tvItemColor;
        @BindView(R.id.tv_color_name)
        TextView tvColorName;
        @BindView(R.id.tv_item_price)
        TextView tvItemPrice;
        @BindView(R.id.iv_item_add)
        ImageView ivItemAdd;
        @BindView(R.id.tv_item_count)
        TextView tvItemCount;
        @BindView(R.id.iv_item_down)
        ImageView ivItemDown;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
