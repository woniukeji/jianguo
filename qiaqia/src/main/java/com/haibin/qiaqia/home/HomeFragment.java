package com.haibin.qiaqia.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haibin.qiaqia.R;
import com.haibin.qiaqia.base.BaseFragment;
import com.haibin.qiaqia.entity.Goods;
import com.haibin.qiaqia.entity.ListChaoCommodity;
import com.haibin.qiaqia.fruitvegetables.FruitVegetableActivity;
import com.haibin.qiaqia.http.HttpMethods;
import com.haibin.qiaqia.http.ProgressSubscriber;
import com.haibin.qiaqia.http.SubscriberOnNextListener;
import com.haibin.qiaqia.main.MainActivity;
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
public class HomeFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    XRecyclerView recyclerview;
    @BindView(R.id.market)
    TextView market;

    private Context context = getActivity();

    //    private String sort[] = {"不限", "默认", "智能", "价格", "发布时间"};
    //    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private List<View> popupViews = new ArrayList<>();
    private HomeAdapter adapter;
    private int lastVisibleItem;
    private LinearLayoutManager mLayoutManager;
    public List<ListChaoCommodity> listChaoCommodities = new ArrayList<ListChaoCommodity>();
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
    SubscriberOnNextListener<Goods> SubListener;
    private View header;
    private ImageView img_friut;
    private RelativeLayout relaFruit;
    private RelativeLayout relaMarket;


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
//                        listChaoCommodities.clear();
//                    }
//                    jobs.getData().getList_t_job();
//                    listChaoCommodities.addAll(jobs.getData().getList_t_job());
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        header = LayoutInflater.from(getActivity()).inflate(R.layout.header_home, null, false);
        img_friut = (ImageView) header.findViewById(R.id.img_friut);
         relaFruit = (RelativeLayout) header.findViewById(R.id.rela_fruit);
        relaMarket = (RelativeLayout) header.findViewById(R.id.rela_market);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    public void initView() {
        adapter = new HomeAdapter(getActivity(), listChaoCommodities);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerview.setHasFixedSize(true);
        //设置布局管理器
        recyclerview.setLayoutManager(mLayoutManager);
        //设置adapter
        recyclerview.setAdapter(adapter);
        //设置Item增加、移除动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.addHeaderView(header);
    }

    public void initData() {
        relaMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MarketActivity.class));
            }
        });
        relaFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), FruitVegetableActivity.class));
            }
        });


        SubListener = new SubscriberOnNextListener<Goods>() {
            @Override
            public void onNext(Goods goodsHttpResult) {
                listChaoCommodities.addAll(goodsHttpResult.getListChaoCommodity());
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "获取成功", Toast.LENGTH_LONG).show();
            }

        };
        HttpMethods.getInstance().getHomeData(new ProgressSubscriber<Goods>(SubListener, getActivity()));

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
