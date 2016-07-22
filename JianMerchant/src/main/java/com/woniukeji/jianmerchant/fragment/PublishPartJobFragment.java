package com.woniukeji.jianmerchant.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.adapter.RecyclerAdapter;
import com.woniukeji.jianmerchant.base.BaseFragment;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.RegionBean;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PublishPartJobFragment extends BaseFragment {

    private static final String PARAM = "type";
    @InjectView(R.id.recycler_region)
    RecyclerView recyclerRegion;
    @InjectView(R.id.recycler_type)
    RecyclerView recyclerType;
    @InjectView(R.id.recycler_jobs)
    RecyclerView recyclerJobs;
    //设置此fragment的参数，创建新兼职，历史纪录，模板
    private String type;
    private List<RegionBean> dataSetRegion = Arrays.asList(new RegionBean("全国"),new RegionBean("三亚"),new RegionBean("海口"),new RegionBean("北京"),new RegionBean("西安"),new RegionBean("杭州"));
    BaseBean<List<RegionBean>> regionBaseBean= new BaseBean<>();



    public static PublishPartJobFragment newInstance(String type) {
        PublishPartJobFragment partJobFragment = new PublishPartJobFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM, type);
        partJobFragment.setArguments(bundle);
        return partJobFragment;



    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString(PARAM);
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (type.equals("cjxjz")) {
            View view = inflater.inflate(R.layout.fragment_create_partjob, container, false);
            ButterKnife.inject(this, view);
            GridLayoutManager regionGridManager = new GridLayoutManager(getHoldingContext(),4);

            recyclerRegion.setLayoutManager(regionGridManager);
            recyclerRegion.setItemAnimator(new DefaultItemAnimator());
            regionBaseBean.setData(dataSetRegion);
            RecyclerAdapter adapter = new RecyclerAdapter(regionBaseBean, getHoldingContext());
            recyclerRegion.setAdapter(adapter);
            return view;

        } else if (type.equals("lsjl")) {


        } else if (type.equals("mb")) {

        }
        TextView view = new TextView(getHoldingContext());
        view.setText(type);

        return view;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
