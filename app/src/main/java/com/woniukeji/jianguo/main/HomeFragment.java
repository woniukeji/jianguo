package com.woniukeji.jianguo.main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdsmdg.tastytoast.TastyToast;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseFragment;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.CityBannerEntity;
import com.woniukeji.jianguo.entity.Jobs;
import com.woniukeji.jianguo.entity.ListTJobEntity;
import com.woniukeji.jianguo.eventbus.CityEvent;
import com.woniukeji.jianguo.eventbus.JobFilterEvent;
import com.woniukeji.jianguo.eventbus.LoginEvent;
import com.woniukeji.jianguo.eventbus.MessageEvent;
import com.woniukeji.jianguo.http.BackgroundSubscriber;
import com.woniukeji.jianguo.http.HttpMethods;
import com.woniukeji.jianguo.http.ProgressSubscriber;
import com.woniukeji.jianguo.http.SubscriberOnNextListener;
import com.woniukeji.jianguo.partjob.PartJobActivity;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.LogUtils;
import com.woniukeji.jianguo.utils.PicassoLoader;
import com.woniukeji.jianguo.utils.SPUtils;
import com.woniukeji.jianguo.utils.UpDialog;
import com.woniukeji.jianguo.widget.CircleImageView;
import com.woniukeji.jianguo.widget.FixedRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.page.OnPageClickListener;
import cn.lightsky.infiniteindicator.page.Page;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener, OnPageClickListener, View.OnClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    @BindView(R.id.list) FixedRecyclerView recycleList;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tv_location) TextView tvLocation;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.rl_top) RelativeLayout rl_top;
    @BindView(R.id.circle_dot) CircleImageView circleDot;
    @BindView(R.id.rl_message) RelativeLayout rlMessage;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private View headerView;
    private HomeJobAdapter adapter;
    private List<Jobs.ListTJobEntity> jobList = new ArrayList<Jobs.ListTJobEntity>();
    private ArrayList<Page> pageViews = new ArrayList<>();
    private InfiniteIndicator mAnimCircleIndicator;
    private InfiniteIndicator mAnimLineIndicator;
    RelativeLayout mHeader;
    InfiniteIndicator mIndicatorDefaultCircle;
    LinearLayout mLlPartTop;
    RelativeLayout mImgGiftsJob;
    RelativeLayout mImgDayJob;
    private RelativeLayout mImgMyJob;
    LinearLayout mLlPartBottom;
    RelativeLayout mImgTravelJob;
    TextView mTvPart3;
    private int lastVisibleItem;
    private LinearLayoutManager mLayoutManager;
    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;
    private int MSG_GET_CITY_SUCCESS = 4;
    private int MSG_GET_CITY_FAIL = 5;
    private Context context = this.getActivity();
    private CityBannerEntity.ListTCityEntity defultCity;
    private List<CityBannerEntity.ListTBannerEntity> banners;
    private String cityName;
    private String cityId;
    private boolean NoGPS = true;
    private int loginId;
    private boolean DataComplete = false;
    private int totalDy;
    private String apkurl;
    private View view;
    private Unbinder bind;
    private SubscriberOnNextListener<CityBannerEntity> cityBannerEntitySubscriberOnNextListener;
    private SubscriberOnNextListener<Jobs> listSubscriberOnNextListener;
    private CityBannerEntity mCityBannerEntity;
    public HomeFragment() {
    }
    public static HomeFragment newInstance(int columnCount) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        int version = (int) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.LOGIN_VERSION, 0);
        apkurl = (String) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.LOGIN_APK_URL, "");
        if (version>getVersion()) {//大于当前版本升级
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("检测到新版本，是否更新？")
                    .setConfirmText("确定")
                    .setCancelText("取消")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            UpDialog upDataDialog = new UpDialog(getActivity(),apkurl);
                            upDataDialog.setCanceledOnTouchOutside(false);
                            upDataDialog.setCanceledOnTouchOutside(false);
                            upDataDialog.show();
                        }
                    }).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_jobitem_list, container, false);
        headerView = inflater.inflate(R.layout.home_header_view, container, false);
        bind = ButterKnife.bind(this, view);//绑定framgent
// Set the adapter
        RelativeLayout rlMessage = (RelativeLayout) view.findViewById(R.id.rl_message);
        rlMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleDot.setVisibility(View.GONE);
                startActivity(new Intent(getContext(), PushMessageActivity.class));
            }
        });
        assignViews(headerView);
        mAnimCircleIndicator = (InfiniteIndicator) headerView.findViewById(R.id.indicator_default_circle);
        mAnimCircleIndicator.setImageLoader(new PicassoLoader());

        mAnimCircleIndicator.setPosition(InfiniteIndicator.IndicatorPosition.Center_Bottom);
        mAnimCircleIndicator.setOnPageChangeListener(this);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

        adapter = new HomeJobAdapter(jobList, getActivity());
        adapter.addHeaderView(headerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
//设置布局管理器
        recycleList.setLayoutManager(mLayoutManager);
//设置adapter
        recycleList.setAdapter(adapter);
//设置Item增加、移除动画
        recycleList.setItemAnimator(new DefaultItemAnimator());
//添加分割线
        refreshLayout.setColorSchemeResources(R.color.app_bg);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HttpMethods.getInstance().getHotJobs(new ProgressSubscriber<Jobs>(listSubscriberOnNextListener,getActivity()),String.valueOf(cityId), "0");
            }
        });
        initData();
        return view;
    }
    @Override
    public int getContentViewId() {
        return view.getId();
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void assignViews(View view) {
        mHeader = (RelativeLayout) view.findViewById(R.id.header);
        mIndicatorDefaultCircle = (InfiniteIndicator) view.findViewById(R.id.indicator_default_circle);
        mLlPartTop = (LinearLayout) view.findViewById(R.id.ll_part_top);
        mImgGiftsJob = (RelativeLayout) view.findViewById(R.id.img_gifts_job);
        mImgDayJob = (RelativeLayout) view.findViewById(R.id.img_day_job);
        mImgTravelJob = (RelativeLayout) view.findViewById(R.id.img_travel_job);
        mImgMyJob = (RelativeLayout) view.findViewById(R.id.img_my_job);
        mLlPartBottom = (LinearLayout) view.findViewById(R.id.ll_part_bottom);
        mTvPart3 = (TextView) view.findViewById(R.id.tv_part3);
        mImgGiftsJob.setOnClickListener(this);
        mImgDayJob.setOnClickListener(this);
        mImgTravelJob.setOnClickListener(this);
        mImgMyJob.setOnClickListener(this);
        tvLocation.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recycleList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (jobList.size() > 5 && lastVisibleItem == jobList.size() + 1 && DataComplete) {
                    HttpMethods.getInstance().getHotJobs(new BackgroundSubscriber<Jobs>(listSubscriberOnNextListener,getActivity()),String.valueOf(cityId), String.valueOf(lastVisibleItem - 1));
                    DataComplete = false;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                totalDy = totalDy + dy;
                //define it for scroll height
                int distance = totalDy;
                if (distance > 0 && distance < 500) {
                    rl_top.getBackground().mutate().setAlpha(distance / 2);
                } else if (distance > 500) {
                    rl_top.getBackground().mutate().setAlpha(255);
                } else {
                    rl_top.getBackground().mutate().setAlpha(0);
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        MainActivity mainActivity = (MainActivity) getActivity();
        switch (view.getId()) {
            case R.id.img_gifts_job:
                Intent intent = new Intent(getActivity(), PartJobActivity.class);
                intent.putExtra("type", 2);
                intent.putExtra("cityid", cityId);
                startActivity(intent);
                break;
            case R.id.img_day_job:
                Intent dayIntent = new Intent(getActivity(), PartJobActivity.class);
                dayIntent.putExtra("type", 5);
                dayIntent.putExtra("cityid", cityId);
                startActivity(dayIntent);
                break;
            case R.id.img_travel_job:
                Intent travelIntent = new Intent(getActivity(), PartJobActivity.class);
                travelIntent.putExtra("type", 3);
                travelIntent.putExtra("cityid", cityId);
                startActivity(travelIntent);
                break;
            case R.id.img_my_job:
                Intent LongIntent = new Intent(getActivity(), PartJobActivity.class);
                LongIntent.putExtra("type", 6);
                LongIntent.putExtra("cityid", cityId);
                startActivity(LongIntent);
                break;
            case R.id.tv_location:
                startActivity(new Intent(getActivity(), CityActivity.class));
        }
    }
    private void initJobDataWithCity(CityBannerEntity.ListTCityEntity defultCity) {
        if (cityName != null && !cityName.equals("")) {
            tvLocation.setText(cityName);
            tvLocation.setText(defultCity.getCity());
            HttpMethods.getInstance().getHotJobs(new ProgressSubscriber<Jobs>(listSubscriberOnNextListener,getActivity()),String.valueOf(defultCity.getCode()), "0");
            return;
        }else {
            HttpMethods.getInstance().getHotJobs(new ProgressSubscriber<Jobs>(listSubscriberOnNextListener,getActivity()),"010", "0");
            tvLocation.setText("北京");
        }
    }
    private void initBannerData(List<CityBannerEntity.ListTBannerEntity> banners) {
        for (int i = 0; i < banners.size(); i++) {
            pageViews.add(new Page(String.valueOf(banners.get(i).getId()), banners.get(i).getImage(), this));
        }
        mAnimCircleIndicator.addPages(pageViews);
    }
    private void initData() {
        cityBannerEntitySubscriberOnNextListener=new SubscriberOnNextListener<CityBannerEntity>() {
            @Override
            public void onNext(CityBannerEntity cityBannerEntity) {
                 mCityBannerEntity=cityBannerEntity;
                banners = cityBannerEntity.getList_t_banner();
                String cityCode = (String) SPUtils.getParam(getActivity(), Constants.USER_INFO, Constants.USER_LOCATION_CODE, "010");
                String cityName = (String) SPUtils.getParam(getActivity(), Constants.USER_INFO, Constants.USER_LOCATION_NAME, "北京");
                defultCity = new CityBannerEntity.ListTCityEntity();
                defultCity.setCode(cityCode);
                defultCity.setCity(cityName.substring(0, cityName.length()));
                //按照默认city初始化兼职数据
                initJobDataWithCity(defultCity);
                initBannerData(banners);
                adapter.notifyDataSetChanged();
                mAnimCircleIndicator.start();
            }
        };
        listSubscriberOnNextListener=new SubscriberOnNextListener<Jobs>() {
            @Override
            public void onNext(Jobs listTJobEntities) {
                if (refreshLayout != null && refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                    jobList.clear();
                }
                listTJobEntities.getList_t_job();
                jobList.addAll(listTJobEntities.getList_t_job());
                adapter.notifyDataSetChanged();
                DataComplete = true;
            }
        };
        loginId = (int) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_USERID, 0);
        cityName = (String) SPUtils.getParam(getActivity(), Constants.USER_INFO, Constants.USER_LOCATION_NAME, "北京");
        cityId = (String) SPUtils.getParam(getActivity(), Constants.USER_INFO, Constants.USER_LOCATION_CODE, "010");
        HttpMethods.getInstance().getCityBanner(new BackgroundSubscriber<CityBannerEntity>(cityBannerEntitySubscriberOnNextListener,getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    /**
     * 顯示極光推送過來的消息提醒
     * 首页信封險是紅點
     */
    public void onEvent(MessageEvent event) {
        circleDot.setVisibility(View.VISIBLE);
    }
    public void onEvent(LoginEvent event) {
        initData();
    }
    public void onEvent(final CityEvent event) {
        String tempCityId = "0";
        int mPosition = 0;
        //手动切换和自动定位点击确定切换两种方式，每种方式执行后需要通知兼职界面更新地址筛选条件
        for (int i = 0; i < mCityBannerEntity.getList_t_city().size(); i++) {
            if (mCityBannerEntity.getList_t_city().get(i).getCity().contains(event.city.getCity())) {
                mPosition = i;
                tempCityId =mCityBannerEntity.getList_t_city().get(i).getCode();
                break;
            }
        }
        if (!event.isGPS) {
            tvLocation.setText(event.city.getCity());
            refreshLayout.setRefreshing(true);
            HttpMethods.getInstance().getHotJobs(new ProgressSubscriber<Jobs>(listSubscriberOnNextListener,getActivity()),String.valueOf(tempCityId), "0");
            JobFilterEvent jobFilterEvent = new JobFilterEvent();
            cityId = tempCityId;
            jobFilterEvent.cityId = tempCityId;
            jobFilterEvent.position = mPosition;
            EventBus.getDefault().post(jobFilterEvent);
        } else if (tempCityId != cityId) {
            final String finalTempCityId = tempCityId;
            final int finalMPosition = mPosition;
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("切换城市到" + event.city.getCity() + "?")
                    .setConfirmText("确定")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            if (tvLocation != null) {
                                tvLocation.setText(event.city.getCity());
                            }
                            cityId = finalTempCityId;
                            refreshLayout.setRefreshing(true);
                            HttpMethods.getInstance().getHotJobs(new ProgressSubscriber<Jobs>(listSubscriberOnNextListener,getActivity()),String.valueOf(finalTempCityId), "0");
                            JobFilterEvent jobFilterEvent = new JobFilterEvent();
                            jobFilterEvent.cityId = finalTempCityId;
                            jobFilterEvent.position = finalMPosition;
                            EventBus.getDefault().post(jobFilterEvent);
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .setCancelText("取消").show();
        }


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAnimCircleIndicator.stop();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    public void onPageClick(int position, Page page) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("url", banners.get(position).getUrl());
        startActivity(intent);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.e("home", "attach");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
        EventBus.getDefault().unregister(this);
        LogUtils.i("fragment", ":onDestroyView");
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
    }
        public int dip2px(Context context, float dipValue) {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int) (scale * dipValue + 0.5f);
        }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public int getVersion() {
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
