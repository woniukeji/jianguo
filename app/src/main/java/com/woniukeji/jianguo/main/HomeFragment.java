package com.woniukeji.jianguo.main;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseFragment;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.CityBannerEntity;
import com.woniukeji.jianguo.entity.Jobs;
import com.woniukeji.jianguo.eventbus.CityEvent;
import com.woniukeji.jianguo.eventbus.JobTypeEvent;
import com.woniukeji.jianguo.mine.MyPartJboActivity;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.LogUtils;
import com.woniukeji.jianguo.utils.PicassoLoader;
import com.woniukeji.jianguo.widget.FixedRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.page.OnPageClickListener;
import cn.lightsky.infiniteindicator.page.Page;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener, OnPageClickListener ,View.OnClickListener{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    @InjectView(R.id.list) FixedRecyclerView recycleList;
    @InjectView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;
    @InjectView(R.id.tv_location) TextView tvLocation;
    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.top) RelativeLayout top;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private View headerView;
    private HomeJobAdapter adapter;
    private List<Jobs.ListTJobEntity> jobList = new ArrayList<Jobs.ListTJobEntity>();
    private ViewPager vp;
    private LinearLayout ll;
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
    private Handler mHandler = new Myhandler(this.getActivity());
    private Context context = this.getActivity();

    @OnClick(R.id.tv_location)
    public void onClick() {
        startActivity(new Intent(getActivity(),CityActivity.class));
    }



    private class Myhandler extends Handler {
        private WeakReference<Context> reference;
        private List<CityBannerEntity.ListTBannerEntity> banners;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity mainActivity = (MainActivity) reference.get();
            switch (msg.what) {
                case 0:
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
                    if (msg.arg1==0){
                        jobList.clear();
                    }
                    BaseBean<Jobs> jobs = (BaseBean<Jobs>) msg.obj;
                    jobs.getData().getList_t_job();
                    jobList.addAll(jobs.getData().getList_t_job());
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
//                    Toast.makeText(mainActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:

                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(mainActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    BaseBean<CityBannerEntity> cityBannerEntityBaseBean = (BaseBean<CityBannerEntity>) msg.obj;
                    banners = cityBannerEntityBaseBean.getData().getList_t_banner();
                    initBannerData(banners);
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }

    private void initBannerData(List<CityBannerEntity.ListTBannerEntity> banners) {
        for (int i = 0; i < banners.size(); i++) {
            pageViews.add(new Page(String.valueOf(banners.get(i).getId()), banners.get(i).getImage(), this));
        }
        mAnimCircleIndicator.addPages(pageViews);
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jobitem_list, container, false);
// Set the adapter
        ButterKnife.inject(this, view);


        headerView = inflater.inflate(R.layout.home_header_view, container, false);

        assignViews(headerView);


        initData();
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
                GetTask getTask = new GetTask("0","0");
                getTask.execute();
            }
        });
        return view;
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

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recycleList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (jobList.size() > 5 && lastVisibleItem == jobList.size()+1) {
                    GetTask getTask=new GetTask("0",String.valueOf(lastVisibleItem));
                    getTask.execute();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void onClick(View view) {
        MainActivity mainActivity= (MainActivity) getActivity();
            switch (view.getId()){
                case R.id.img_gifts_job:
                    mainActivity.getMainPager().setCurrentItem(1);
                    JobTypeEvent jobTypeEvent=new JobTypeEvent();
                    jobTypeEvent.fragmentHotType=2;//热门（0=普通，1=热门，2=精品，3=旅行）

                    EventBus.getDefault().post(jobTypeEvent);
                break;
                case R.id.img_day_job:
                    JobTypeEvent jobTypeEv=new JobTypeEvent();
                    jobTypeEv.fragmentHotType=4;
                    EventBus.getDefault().post(jobTypeEv);
                    mainActivity.getMainPager().setCurrentItem(1);
                    break;
                case R.id.img_travel_job:
                    JobTypeEvent jobType=new JobTypeEvent();
                    jobType.fragmentHotType=3;
                    EventBus.getDefault().post(jobType);
                    mainActivity.getMainPager().setCurrentItem(1);
                    break;
                case R.id.img_my_job:
                   startActivity(new Intent(getActivity(), MyPartJboActivity.class));
                    break;

            }
    }

    private void initData() {
        GetCityTask getCityTask = new GetCityTask();
        getCityTask.execute();
        GetTask getTask = new GetTask("1","0");
        getTask.execute();
    }

    @Override
    public void onResume() {
        mAnimCircleIndicator.start();
        super.onResume();
    }
    public void onEvent(CityEvent event) {
        tvLocation.setText(event.city.getCity());
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

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
        LogUtils.i("fragment", ":onDestroyView");
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
    }

    public class GetTask extends AsyncTask<Void, Void, Void> {
        private final String type;
        private final String count;

        GetTask(String type,String count) {
            this.type = type;
            this.count=count;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                getJobs();
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            if (!refreshLayout.isRefreshing()){
                refreshLayout.setProgressViewOffset(false, 0,dip2px(getActivity(), 24));
                refreshLayout.setRefreshing(true);
            }
            super.onPreExecute();
        }
        public  int dip2px(Context context, float dipValue) {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int) (scale * dipValue + 0.5f);
        }
        /**
         * postInfo
         */
        public void getJobs() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.GET_JOB)
                    .addParams("only", only)
                    .addParams("hot", "1")
                    .addParams("count", count)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<Jobs>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<Jobs>>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_GET_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean baseBean) {
                            if (baseBean.getCode().equals("200")) {
//                                SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                                Message message = new Message();
                                message.obj = baseBean;
                                message.arg1= Integer.parseInt(count);
                                message.what = MSG_GET_SUCCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_GET_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }
    }

    public class GetCityTask extends AsyncTask<Void, Void, Void> {
        GetCityTask() {
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                getCitys();
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        /**
         * postInfo
         */
        public void getCitys() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.GET_CITY)
                    .addParams("only", only)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<CityBannerEntity>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<CityBannerEntity>>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_GET_CITY_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean baseBean) {
                            if (baseBean.getCode().equals("200")) {
//                                SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                                Message message = new Message();
                                message.obj = baseBean;
                                message.what = MSG_GET_CITY_SUCCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_GET_CITY_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }
    }
}
