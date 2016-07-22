package com.woniukeji.jianmerchant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.TestActivity;
import com.woniukeji.jianmerchant.adapter.JobsAdapter;
import com.woniukeji.jianmerchant.adapter.MyTypeAdapter;
import com.woniukeji.jianmerchant.adapter.RegionAdapter;
import com.woniukeji.jianmerchant.base.BaseFragment;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.CityAndCategoryBean;
import com.woniukeji.jianmerchant.entity.RegionBean;
import com.woniukeji.jianmerchant.entity.TypeBean;
import com.woniukeji.jianmerchant.http.HttpMethods;
import com.woniukeji.jianmerchant.http.ProgressSubscriber;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.LogUtils;
import com.woniukeji.jianmerchant.utils.SPUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PublishPartJobFragment extends BaseFragment implements View.OnClickListener {

    private static final String PARAM = "type";
    @InjectView(R.id.recycler_region)
    RecyclerView recyclerRegion;
    @InjectView(R.id.recycler_type)
    RecyclerView recyclerType;
    @InjectView(R.id.recycler_jobs)
    RecyclerView recyclerJobs;
    @InjectView(R.id.next_page)
    TextView nextPage;
    //设置此fragment的参数，创建新兼职，历史纪录，模板
    private String type;
    private List<RegionBean> dataSetRegion = Arrays.asList(new RegionBean("全国",0),new RegionBean("三亚",1),new RegionBean("海口",2),new RegionBean("北京",3),new RegionBean("西安",4),new RegionBean("杭州",5));
    private List<TypeBean> dataSetType = Arrays.asList(new TypeBean("短期",0), new TypeBean("长期",1), new TypeBean("实习生",2), new TypeBean("寒/暑假工",3));
    BaseBean<List<RegionBean>> regionBaseBean= new BaseBean<>();
    BaseBean<List<TypeBean>> typeBaseBean = new BaseBean<>();
    BaseBean<List<CityAndCategoryBean.ListTTypeBean>> categoryBean = new BaseBean<>();
    //next step Activity
    private Intent intent;
    private Bundle bundle;


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
            RegionAdapter adapterRegion = new RegionAdapter(regionBaseBean, getHoldingContext());
            recyclerRegion.setAdapter(adapterRegion);

            GridLayoutManager typeGridManager = new GridLayoutManager(getHoldingContext(), 4);
            recyclerType.setLayoutManager(typeGridManager);
            recyclerType.setItemAnimator(new DefaultItemAnimator());
            typeBaseBean.setData(dataSetType);
            MyTypeAdapter typeAdapter = new MyTypeAdapter(typeBaseBean, getHoldingContext());
            recyclerType.setAdapter(typeAdapter);

            GridLayoutManager jobsGridManager = new GridLayoutManager(getHoldingContext(), 4);
            recyclerJobs.setLayoutManager(jobsGridManager);
            recyclerJobs.setItemAnimator(new DefaultItemAnimator());


            //访问网络+设置recyclerjobs的数据
            getCategoryToBean();
            nextPage.setOnClickListener(this);






            return view;

        } else if (type.equals("lsjl")) {


        } else if (type.equals("mb")) {

        }
        TextView view = new TextView(getHoldingContext());
        view.setText(type);


        return view;


    }

    /**
     * 访问网络获取兼职类别
     */
    private void getCategoryToBean() {
        ProgressSubscriber.SubscriberOnNextListenner<CityAndCategoryBean> onNextListenner = new ProgressSubscriber.SubscriberOnNextListenner<CityAndCategoryBean>() {

            @Override
            public void onNext(CityAndCategoryBean cityAndCategoryBean) {
                List<CityAndCategoryBean.ListTTypeBean> typeList = cityAndCategoryBean.getList_t_type();
                categoryBean.setData(typeList);
                JobsAdapter jobsAdapter = new JobsAdapter(categoryBean, getHoldingContext());
                recyclerJobs.setAdapter(jobsAdapter);
            }
        };

        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        int loginId = (int) SPUtils.getParam(getHoldingContext(), Constants.LOGIN_INFO, Constants.SP_USERID, 0);
        HttpMethods.getInstance().getCityAndCategory(new ProgressSubscriber<CityAndCategoryBean>(onNextListenner,getHoldingContext()),only,String.valueOf(loginId));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_page:
                if (CheckAndGet()) {
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * true 地区，类型，岗位都选择了一项。
     * @return
     */
    private boolean CheckAndGet() {
        intent = new Intent(getHoldingContext(), TestActivity.class);
        bundle = new Bundle();

        for (int i = 0; i < regionBaseBean.getData().size(); i++) {
                LogUtils.i("regionBaseBean",regionBaseBean.getData().get(i).toString());
            if (regionBaseBean.getData().get(i).isSelect()) {
                bundle.putString("region", regionBaseBean.getData().get(i).getRegion());
                bundle.putInt("region_id", regionBaseBean.getData().get(i).getId());
                break;
            } else if (i == regionBaseBean.getData().size()-1){
                Toast.makeText(getHoldingContext(), "请选择地区", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        for (int i = 0; i < typeBaseBean.getData().size(); i++) {
                LogUtils.i("typeBaseBean",typeBaseBean.getData().get(i).toString());
            if (typeBaseBean.getData().get(i).isSelect()) {
                bundle.putString("type", typeBaseBean.getData().get(i).getType());
                bundle.putInt("type_id", typeBaseBean.getData().get(i).getId());
                break;
            } else if (i==typeBaseBean.getData().size()-1){
                Toast.makeText(getHoldingContext(), "请选择类型", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        for (int i = 0; i < categoryBean.getData().size(); i++) {
            LogUtils.i("categoryBean",categoryBean.getData().get(i).toString());
            if (categoryBean.getData().get(i).isSelect()) {
                bundle.putString("category", categoryBean.getData().get(i).getType_name());
                bundle.putInt("category_id",categoryBean.getData().get(i).getId());
                break;
            } else if (i==categoryBean.getData().size()-1) {
                Toast.makeText(getHoldingContext(), "请选择岗位", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        intent.putExtras(bundle);
        return true;
    }
}
