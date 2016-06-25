package com.haibin.qiaqia.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.haibin.qiaqia.R;
import com.haibin.qiaqia.base.BaseActivity;
import com.haibin.qiaqia.entity.CategoryGoods;
import com.haibin.qiaqia.http.HttpMethods;
import com.haibin.qiaqia.http.ProgressSubscriber;
import com.haibin.qiaqia.http.SubscriberOnNextListener;
import com.haibin.qiaqia.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/25 0025.
 */


public class CategrayGoodsActivity extends BaseActivity {

    @BindView(R.id.bt_dian)
    Button btDian;
    @BindView(R.id.bt_dian2)
    Button btDian2;
    @BindView(R.id.bt_dian3)
    Button btDian3;
    @BindView(R.id.tv_data)
    TextView tvData;
    private SubscriberOnNextListener<CategoryGoods> categrayGoodsActivity;

    @Override
    public void setContentView() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {
//处理接口返回的数据
        categrayGoodsActivity = new SubscriberOnNextListener<CategoryGoods>() {

            @Override
            public void onNext(CategoryGoods categoryGoods) {
                    tvData.setText(categoryGoods.getList_chao_commodity().get(0).getName());
            }
        };
    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        setContentView(R.layout.categraygoods);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_dian, R.id.bt_dian2, R.id.bt_dian3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_dian:
                HttpMethods.getInstance().GetCategoryGoods(new ProgressSubscriber<CategoryGoods>(categrayGoodsActivity, CategrayGoodsActivity.this), "4", "1");
                break;
            case R.id.bt_dian2:
                break;
            case R.id.bt_dian3:
                break;
        }
    }
}
