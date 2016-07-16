package com.haibin.qiaqia.fruitvegetables;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haibin.qiaqia.R;
import com.haibin.qiaqia.entity.ListChaoCommodity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/10 0010.
 */

public class DisplayDialog extends Dialog {
    @BindView(R.id.iv_picture)
    ImageView ivPicture;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.iv_attention)
    ImageView ivAttention;
    @BindView(R.id.tv_describe)
    TextView tvDescribe;
    @BindView(R.id.iv_open)
    ImageView ivOpen;
    @BindView(R.id.iv_add)
    ImageView ivAdd;

    @OnClick({R.id.iv_attention, R.id.iv_open, R.id.iv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_attention:
                break;
            case R.id.iv_open:
                break;
            case R.id.iv_add:
                tvDescribe.setMaxLines(20);

                break;
        }
    }

    //增加一个回调函数,用以从外部接收返回值
    public interface IDisplayDialogEventListener {
        public void displayDialogEvent(int id);
    }

    Context mContext;
    ListChaoCommodity mdata;
    private IDisplayDialogEventListener mDisplayDialogEventListener;

    public DisplayDialog(Context context) {
        super(context);
        mContext = context;
    }
    public DisplayDialog(Context context,ListChaoCommodity data, IDisplayDialogEventListener listener) {
        super(context);
        mContext = context;
        mdata = data;
        mDisplayDialogEventListener = listener;
    }
    public DisplayDialog(Context context, ListChaoCommodity data, IDisplayDialogEventListener listener, int themeResId) {
        super(context, R.style.myDialogTheme);
        mContext = context;
        mdata = data;
        mDisplayDialogEventListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View display_dialog = LayoutInflater.from(mContext).inflate(R.layout.display_dialog, null);
        this.setContentView(display_dialog);
        ButterKnife.bind(this, display_dialog);
        Glide.with(mContext)
                .load(mdata.getImage())
                .placeholder(R.drawable.ic_loading_rotate)
                .crossFade()
                .into(ivPicture);
        tvPrice.setText("￥"+String.valueOf(mdata.getPrice()));
        tvDescribe.setText(mdata.getName());

    }
}
