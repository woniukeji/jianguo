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

import com.haibin.qiaqia.R;
import com.haibin.qiaqia.base.BaseFragment;
import com.haibin.qiaqia.entity.User;
import com.haibin.qiaqia.main.MainActivity;
import com.haibin.qiaqia.utils.LogUtils;
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
    private Context context = getActivity();

    private String headers[] = {"职业", "排序", "地区"};
    //    private String sort[] = {"不限", "默认", "智能", "价格", "发布时间"};
    //    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private List<View> popupViews = new ArrayList<>();
    private CartAdapter adapter;
    private int lastVisibleItem;
    private LinearLayoutManager mLayoutManager;
    public List<User> jobList = new ArrayList<User>();
    private String cityid = "1";
    String typeid = "0";
    String areid = "0";
    String filterid = "2";

    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;
    private int MSG_CITY_SUCCESS = 2;
    private int MSG_CITY_FAIL = 3;
    private Handler mHandler = new Myhandler(this.getActivity());
    private int mtype = 0;
    private int position;
    private boolean DataComplete = false;

    private class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity mainActivity = (MainActivity) reference.get();
            switch (msg.what) {
                case 0:
//                    if (refreshLayout!=null&&refreshLayout.isRefreshing()) {
//                        refreshLayout.setRefreshing(false);
//                    }
//                    BaseBean<Jobs> jobs = (BaseBean<Jobs>) msg.obj;
//                    int count = msg.arg1;
//                    if (count == 0) {
//                        jobList.clear();
//                    }
//                    jobs.getData().getList_t_job();
//                    jobList.addAll(jobs.getData().getList_t_job());
//                    adapter.notifyDataSetChanged();
//                    DataComplete=true;
                    break;
                case 1:
                    //                    String ErrorMessage = (String) msg.obj;
                    //                    Toast.makeText(mainActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
//                    cityCategoryBaseBean = (BaseBean<CityCategory>) msg.obj;
//                    initDrawData(cityCategoryBaseBean);
                    break;
                case 3:
                    String sms = (String) msg.obj;
//                    Toast.makeText(mainActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void initView() {
//        adapter = new CartAdapter(jobList, getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        recyclerview.setLayoutManager(mLayoutManager);
        //设置adapter
        recyclerview.setAdapter(adapter);
        //设置Item增加、移除动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void initData() {

    }


    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //refresh data here
            }

            @Override
            public void onLoadMore() {
                // load more data here
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }


}
