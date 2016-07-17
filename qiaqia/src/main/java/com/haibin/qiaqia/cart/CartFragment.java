package com.haibin.qiaqia.cart;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.haibin.qiaqia.R;
import com.haibin.qiaqia.base.BaseFragment;
import com.haibin.qiaqia.base.Constants;
import com.haibin.qiaqia.entity.Goods;
import com.haibin.qiaqia.entity.ListChaoCommodity;
import com.haibin.qiaqia.entity.User;
import com.haibin.qiaqia.http.HttpMethods;
import com.haibin.qiaqia.http.ProgressSubscriber;
import com.haibin.qiaqia.http.SubscriberOnNextListener;
import com.haibin.qiaqia.main.MainActivity;
import com.haibin.qiaqia.utils.LogUtils;
import com.haibin.qiaqia.utils.SPUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link } subclass.
 */
public class CartFragment extends BaseFragment {
    @BindView(R.id.recyclerview) XRecyclerView recyclerview;
    private CartAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    public List<ListChaoCommodity> listChaoCommodities = new ArrayList<ListChaoCommodity>();
    SubscriberOnNextListener<Goods> SubListener;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    public void initView() {
        adapter = new CartAdapter(getActivity(),listChaoCommodities);
        mLayoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        recyclerview.setLayoutManager(mLayoutManager);
        //设置adapter
        recyclerview.setAdapter(adapter);
        //设置Item增加、移除动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());
    }

    public void initData() {

            int loginId = (int) SPUtils.getParam(getActivity(), Constants.USER_INFO, Constants.INFO_ID, 0);

            SubListener = new SubscriberOnNextListener<Goods>() {
                @Override
                public void onNext(Goods goodsHttpResult) {
                    listChaoCommodities.addAll(goodsHttpResult.getListChaoCommodity());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "获取成功", Toast.LENGTH_LONG).show();
                }
            };
            HttpMethods.getInstance().getCarInfo(new ProgressSubscriber<Goods>(SubListener ,getActivity()), String.valueOf(loginId) );

    }

}
