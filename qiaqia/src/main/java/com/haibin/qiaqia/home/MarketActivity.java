package com.haibin.qiaqia.home;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.haibin.qiaqia.R;
import com.haibin.qiaqia.base.BaseActivity;
import com.haibin.qiaqia.base.Constants;
import com.haibin.qiaqia.entity.Goods;
import com.haibin.qiaqia.entity.ListChaoCommodity;
import com.haibin.qiaqia.entity.ListMarket;
import com.haibin.qiaqia.entity.Market;
import com.haibin.qiaqia.http.HttpMethods;
import com.haibin.qiaqia.http.ProgressSubscriber;
import com.haibin.qiaqia.http.SubscriberOnNextListener;
import com.haibin.qiaqia.listener.MyItemClickListener;
import com.haibin.qiaqia.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cai on 2016/6/25.
 */

public class MarketActivity extends BaseActivity implements MyItemClickListener{

    @BindView(R.id.market_rv)
    RecyclerView marketRv;
    @BindView(R.id.recyclerview_goods)
    RecyclerView recyclerview_goods;
    private SubscriberOnNextListener<Market> SubListener;
    private SubscriberOnNextListener<Goods> GoodsSubListener;
    private List<ListMarket> list_chao_class;
    private List<ListChaoCommodity> list_goods_class= new ArrayList<ListChaoCommodity>();
    private MarketClassAdapter adapter;
    private MarketGoodsAdapter goodsAdapter;
    private List<ListMarket> marketList = new ArrayList<ListMarket>();
    private int loginId;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_market);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        adapter = new MarketClassAdapter(this,marketList,this);
        goodsAdapter=new MarketGoodsAdapter(this,list_goods_class);
        LinearLayoutManager manage = new LinearLayoutManager(this);
        marketRv.setLayoutManager(manage);
        marketRv.setAdapter(adapter);
        marketRv.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manage1 = new LinearLayoutManager(this);
        recyclerview_goods.setLayoutManager(manage1);
        recyclerview_goods.setAdapter(goodsAdapter);
        recyclerview_goods.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

        loginId = (int) SPUtils.getParam(MarketActivity.this, Constants.USER_INFO, Constants.INFO_ID, 0);
        GoodsSubListener=new SubscriberOnNextListener<Goods>() {
            @Override
            public void onNext(Goods goods) {
//                Toast.makeText(MarketActivity.this, goods.toString(), Toast.LENGTH_SHORT).show();
                list_goods_class.clear();
                list_goods_class.addAll(goods.getListChaoCommodity());
                goodsAdapter.notifyDataSetChanged();
            }
        };
        SubListener = new SubscriberOnNextListener<Market>() {
            @Override
            public void onNext(Market market) {
//                Toast.makeText(MarketActivity.this, market.toString(), Toast.LENGTH_SHORT).show();
                list_chao_class = market.getList_chao_class();
                marketList.addAll(list_chao_class);
                adapter.notifyDataSetChanged();
            }
        };
        HttpMethods.getInstance().getMarketClass(new ProgressSubscriber<Market>(SubListener, this), "0");
        HttpMethods.getInstance().getGoods(new ProgressSubscriber<Goods>(GoodsSubListener, this), String.valueOf(loginId),"1");


    }

    @Override
    public void addActivity() {

    }

    @Override
    public void onItemClick(View view, int position) {

        HttpMethods.getInstance().getGoods(new ProgressSubscriber<Goods>(GoodsSubListener, this), String.valueOf(loginId),marketList.get(position).getClass_id());
        adapter.changeSelected(position);
    }


}
