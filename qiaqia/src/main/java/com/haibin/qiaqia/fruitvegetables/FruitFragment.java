package com.haibin.qiaqia.fruitvegetables;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.haibin.qiaqia.R;
import com.haibin.qiaqia.base.BaseFragment;
import com.haibin.qiaqia.base.Constants;
import com.haibin.qiaqia.entity.CategoryGoods;
import com.haibin.qiaqia.entity.Goods;
import com.haibin.qiaqia.entity.ListChaoCommodity;
import com.haibin.qiaqia.home.HomeAdapter;
import com.haibin.qiaqia.home.MarketActivity;
import com.haibin.qiaqia.http.HttpMethods;
import com.haibin.qiaqia.http.ProgressSubscriber;
import com.haibin.qiaqia.http.SubscriberOnNextListener;
import com.haibin.qiaqia.main.MainActivity;
import com.haibin.qiaqia.utils.SPUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/7/9 0009.
 */

public class FruitFragment extends BaseFragment {
    @BindView(R.id.recyclerview_fruit)
    XRecyclerView recyclerviewFruit;
    private LinearLayoutManager mLayoutManager;
    private HomeAdapter adapter;
    private RelativeLayout relaFruit;
    private RelativeLayout relaVegetable;
    public List<ListChaoCommodity> listChaoCommodities = new ArrayList<ListChaoCommodity>();
    SubscriberOnNextListener<Goods> SubListener;
    private String mTitle  = null;

    public static FruitFragment getInstance(String title) {
        FruitFragment fft = new FruitFragment();
        fft.mTitle = title;
        return fft;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fruit, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }



    private void initView() {
        adapter = new HomeAdapter(getActivity(), listChaoCommodities);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerviewFruit.setHasFixedSize(true);
        //设置布局管理器
        recyclerviewFruit.setLayoutManager(mLayoutManager);
        //设置adapter
        recyclerviewFruit.setAdapter(adapter);
        //设置Item增加、移除动画
        recyclerviewFruit.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new HomeAdapter.OnRecyclerViewItemClickListener(){
            @Override
            public void onItemClick(View view , ListChaoCommodity data){
//                Toast.makeText(getActivity(), data.getName(),Toast.LENGTH_SHORT).show();
                DisplayDialog displayDialog = new DisplayDialog(getActivity(),data,new DisplayDialog.IDisplayDialogEventListener(){

                    @Override
                    public void displayDialogEvent(int id) {

                    }
                },R.style.alert_dialog);
                displayDialog.show();
            }
        });
    }
    private void initData() {
        int loginId = (int) SPUtils.getParam(getActivity(), Constants.USER_INFO, Constants.INFO_ID, 0);

        SubListener = new SubscriberOnNextListener<Goods>() {
            @Override
            public void onNext(Goods goodsHttpResult) {
                listChaoCommodities.addAll(goodsHttpResult.getListChaoCommodity());
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "获取成功", Toast.LENGTH_LONG).show();
            }
        };
        HttpMethods.getInstance().getGoods(new ProgressSubscriber<Goods>(SubListener ,getActivity()), String.valueOf(loginId),mTitle );
    }
}
