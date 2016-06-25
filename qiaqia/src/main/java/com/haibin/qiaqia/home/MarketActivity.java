package com.haibin.qiaqia.home;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.haibin.qiaqia.R;
import com.haibin.qiaqia.base.BaseActivity;
import com.haibin.qiaqia.entity.ListMarket;
import com.haibin.qiaqia.entity.Market;
import com.haibin.qiaqia.http.HttpMethods;
import com.haibin.qiaqia.http.ProgressSubscriber;
import com.haibin.qiaqia.http.SubscriberOnNextListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cai on 2016/6/25.
 */

public class MarketActivity extends BaseActivity {

    @BindView(R.id.market_rv)
    RecyclerView marketRv;
    @BindView(R.id.market_fl)
    FrameLayout marketFl;
    private SubscriberOnNextListener<Market> SubListener;
    private List<ListMarket> list_chao_class;
    private MarketClassAdapter adapter;
    private List<ListMarket> marketList = new ArrayList<ListMarket>();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_market);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        adapter = new MarketClassAdapter(this,marketList);

        LinearLayoutManager manage = new LinearLayoutManager(this);
        marketRv.setLayoutManager(manage);
        marketRv.setAdapter(adapter);
        marketRv.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        SubListener = new SubscriberOnNextListener<Market>() {
            @Override
            public void onNext(Market market) {
                Toast.makeText(MarketActivity.this, market.toString(), Toast.LENGTH_SHORT).show();
                list_chao_class = market.getList_chao_class();
                marketList.addAll(list_chao_class);
                adapter.notifyDataSetChanged();
            }
        };
        HttpMethods.getInstance().getMarketClass(new ProgressSubscriber<Market>(SubListener, this), "0");
    }

    @Override
    public void addActivity() {

    }

}
