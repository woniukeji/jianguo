package com.woniukeji.jianguo.partjob;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayfang.dropdownmenu.DropDownMenu;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.CityCategory;
import com.woniukeji.jianguo.entity.Jobs;
import com.woniukeji.jianguo.eventbus.CityJobTypeEvent;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.widget.FixedRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link } subclass.
 */
public class PartJobActivity extends BaseActivity {

    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.list) FixedRecyclerView list;
    @InjectView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;
    @InjectView(R.id.img_share) ImageView imgShare;
    @InjectView(R.id.img_menu) ImageView imgMenu;
    @InjectView(R.id.img_renwu) ImageView imgRenwu;
    @InjectView(R.id.rl_null) RelativeLayout rlNull;
    private String headers[] = {"职业", "排序", "地区"};
    private PartJobAdapter adapter;
    private int lastVisibleItem;
    private LinearLayoutManager mLayoutManager;
    public List<Jobs.ListTJobEntity> jobList = new ArrayList<Jobs.ListTJobEntity>();
    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;
    private int MSG_CITY_SUCCESS = 2;
    private int MSG_CITY_FAIL = 3;
    BaseBean<CityCategory> cityCategoryBaseBean;
    private Handler mHandler = new Myhandler(this);
    private DropDownMenu mMenu;
    private int cityid=3;
    private int type= 0;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }


    private class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PartJobActivity mainActivity = (PartJobActivity) reference.get();
            switch (msg.what) {
                case 0:
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
                    int count=msg.arg1;
                    if (count==0){
                        jobList.clear();
                    }
                    BaseBean<Jobs> jobs = (BaseBean<Jobs>) msg.obj;
                    if (jobs.getData().getList_t_job().size() == 0) {
                        rlNull.setVisibility(View.VISIBLE);
                    }
                    jobList.addAll(jobs.getData().getList_t_job());
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
//                    String ErrorMessage = (String) msg.obj;
//                    Toast.makeText(mainActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    cityCategoryBaseBean = (BaseBean<CityCategory>) msg.obj;
                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(mainActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }


    }


    @Override
    public void addActivity() {

    }


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_part_job);
        ButterKnife.inject(this);
//        EventBus.getDefault().register(this);
//        initDropDownView();
    }

    @Override
    public void initViews() {
        adapter = new PartJobAdapter(jobList, this);
        mLayoutManager = new LinearLayoutManager(this);
//设置布局管理器
        list.setLayoutManager(mLayoutManager);
//设置adapter
        list.setAdapter(adapter);
//设置Item增加、移除动画
        list.setItemAnimator(new DefaultItemAnimator());
//添加分割线
//        recycleList.addItemDecoration(new RecyclerView.ItemDecoration() {
//        });
//        recycleList.addItemDecoration(new DividerItemDecoration(
//                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        refreshLayout.setColorSchemeResources(R.color.app_bg);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetTask getTask = new GetTask(String.valueOf(type), "0");
                getTask.execute();
            }
        });
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
          cityid = intent.getIntExtra("cityid", 3);
         type = intent.getIntExtra("type", 2);
        if (type==3){
            tvTitle.setText("兼职旅行");
        }else if(type==5){
            tvTitle.setText("日结兼职");
        }else{
            tvTitle.setText("精品兼职");
        }
        GetTask getTask = new GetTask(String.valueOf(type), "0");
        getTask.execute();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void onStart() {
        super.onStart();
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (jobList.size() > 5 && lastVisibleItem == jobList.size() + 1) {
                    GetTask getTask = new GetTask(String.valueOf(type),String.valueOf(lastVisibleItem));
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

    private void initDropDownView() {
//        final ListView cityView = new ListView(getActivity());
//        cityAdapter = new GirdDropDownAdapter(getActivity(), Arrays.asList(citys));
//        cityView.setDividerHeight(0);
//        cityView.setAdapter(cityAdapter);
//        final ListView sortView = new ListView(getActivity());
//        sortView.setDividerHeight(0);
//        sortAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(sort));
//        sortView.setAdapter(sortAdapter);

        //init sex menu
        mMenu = (DropDownMenu) findViewById(R.id.menu);
        mMenu.setmMenuCount(3);
        mMenu.setmShowCount(6);
        mMenu.setShowCheck(true);//是否显示展开list的选中项
        mMenu.setmMenuTitleTextSize(14);//Menu的文字大小
        mMenu.setmMenuTitleTextColor(Color.BLACK);//Menu的文字颜色
        mMenu.setmMenuListTextSize(12);//Menu展开list的文字大小
        mMenu.setmMenuListTextColor(Color.BLACK);//Menu展开list的文字颜色
        mMenu.setmMenuBackColor(Color.WHITE);//Menu的背景颜色
        mMenu.setmMenuPressedBackColor(Color.GRAY);//Menu按下的背景颜色
        mMenu.setmCheckIcon(R.drawable.ico_make);//Menu展开list的勾选图片
        mMenu.setmUpArrow(R.drawable.arrow_up);//Menu默认状态的箭头
        mMenu.setmDownArrow(R.drawable.arrow_down);//Menu按下状态的箭头
        mMenu.setDefaultMenuTitle(headers);//默认未选择任何过滤的Menu title
//        List<String[]> items = new ArrayList<>();
//        items.add(jobs);
//        items.add(sort);
//        items.add(citys);
//        mMenu.setmMenuItems(items);
//        mMenu.setIsDebug(false);
    }

//    public void onEvent(CityJobTypeEvent event) {
//        event.cityCategory.getList_t_city2().get(0).getList_t_area();
//        event.cityCategory.getList_t_type().get(0).getId();
//        if (type == 4) {
//            GetTask getTask = new GetTask(String.valueOf(type), "0");
//            getTask.execute();
//        } else {
//            GetTask getTask = new GetTask(String.valueOf(type), "0");
//            getTask.execute();
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
//        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }


    public class GetTask extends AsyncTask<Void, Void, Void> {
        private String type;
        private String count;

        GetTask(String type, String count) {
            this.type = type;
            this.count = count;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                if (type.equals("5")) {
                    getDayJobs();
                } else
                    getJobs();
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
        public void getDayJobs() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.GET_JOB_DAY)
                    .addParams("only", only)
                    .addParams("mode", "2")
                    .addParams("city_id", String.valueOf(cityid))
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

        /**
         * postInfo
         */
        public void getJobs() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.GET_JOB)
                    .addParams("only", only)
                    .addParams("hot", type)
                    .addParams("city_id", String.valueOf(cityid))
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

}
