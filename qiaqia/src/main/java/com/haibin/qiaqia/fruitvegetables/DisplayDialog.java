package com.haibin.qiaqia.fruitvegetables;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.haibin.qiaqia.R;
import com.haibin.qiaqia.base.Constants;
import com.haibin.qiaqia.base.FragmentText;
import com.haibin.qiaqia.entity.Goods;
import com.haibin.qiaqia.entity.ListChaoCommodity;
import com.haibin.qiaqia.http.HttpMethods;
import com.haibin.qiaqia.http.ProgressSubscriber;
import com.haibin.qiaqia.http.SubscriberOnNextListener;
import com.haibin.qiaqia.utils.SPUtils;

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
    SubscriberOnNextListener<Goods> SubListener;

    @OnClick({R.id.iv_attention, R.id.iv_open, R.id.iv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_attention:
                break;
            case R.id.iv_open:
                tvDescribe.setMaxLines(20);
                break;
            case R.id.iv_add:
                int loginId = (int) SPUtils.getParam(mContext, Constants.USER_INFO, Constants.INFO_ID, 0);
                int commodityid = mdata.getId();
//                int count = mdata.getCount();
                int count = 1;
                SubListener = new SubscriberOnNextListener<Goods>() {
                    @Override
                    public void onNext(Goods goodsHttpResult) {
                        Toast.makeText(mContext, "添加购物车成功", Toast.LENGTH_SHORT).show();
                    }
                };
                HttpMethods.getInstance().getChangeCarGoods(new ProgressSubscriber<Goods>(SubListener ,mContext), String.valueOf(loginId),String.valueOf(commodityid),String.valueOf(count) );

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
