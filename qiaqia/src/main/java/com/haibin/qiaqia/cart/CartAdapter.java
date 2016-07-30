package com.haibin.qiaqia.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haibin.qiaqia.R;
import com.haibin.qiaqia.entity.ListChaoCommodity;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {


    @BindView(R.id.cart_cb)
    CheckBox cartCb;
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
    private Context context;
    private List<ListChaoCommodity> list;
    private OnGoodsAMLitener mOnItemClickListener = null;
    private boolean isDelete = false;

    public interface OnGoodsAMLitener {
        //减商品
        void minusGoods(int postion);
        //加商品
        void addGoods(int postion);
        //删除商品
        void deleteGoods(int postion);
    }

    public CartAdapter(Context context, List<ListChaoCommodity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_cart, null);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (isDelete){
            holder.cart_cb.setVisibility(View.VISIBLE);
        }else{
            holder.cart_cb.setVisibility(View.GONE);
        }

        final ListChaoCommodity data = list.get(position);
        holder.tvItemName.setText(data.getName());
        holder.tvColorName.setText(data.getAlias());
        holder.tvItemPrice.setText(mul(data.getPrice(), data.getCount()) + "元");
        holder.tvItemCount.setText(String.valueOf(data.getCount()));
        Glide.with(context)
                .load(data.getImage())
                .placeholder(R.drawable.ic_loading_rotate)
                .crossFade()
                .into(holder.ivItemPic);
        holder.ivItemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.addGoods(position);
            }
        });
        holder.ivItemDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getCount() > 1) {
                    mOnItemClickListener.minusGoods(position);
                }
            }
        });
        holder.cart_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mOnItemClickListener.deleteGoods(position);
            }
        });
//        holder.itemView.setTag(data);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnGoodsListener(OnGoodsAMLitener mOnItemClickListener) {
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
        @BindView(R.id.cart_cb)
        CheckBox cart_cb;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public void isShowChexkBox(boolean isShow){
        isDelete = isShow;
    }

    public static double mul(double v1, int v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }
}
