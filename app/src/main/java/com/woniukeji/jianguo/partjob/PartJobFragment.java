package com.woniukeji.jianguo.partjob;


import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayfang.dropdownmenu.BaseEntity;
import com.jayfang.dropdownmenu.DropDownMenu;
import com.jayfang.dropdownmenu.OnMenuSelectedListener;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseFragment;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.CityCategory;
import com.woniukeji.jianguo.entity.Jobs;
import com.woniukeji.jianguo.entity.SpinnerEntity;
import com.woniukeji.jianguo.eventbus.CityEvent;
import com.woniukeji.jianguo.eventbus.CityJobTypeEvent;
import com.woniukeji.jianguo.eventbus.JobFilterEvent;
import com.woniukeji.jianguo.main.MainActivity;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.LogUtils;
import com.woniukeji.jianguo.utils.SPUtils;
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
public class PartJobFragment extends BaseFragment {
    private Context context = getActivity();
    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.list) FixedRecyclerView list;
    @InjectView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;
    private String headers[] = {"职业", "排序", "地区"};
    private List<BaseEntity> jobs = new ArrayList<>();
    private List<BaseEntity> sort = new ArrayList<>();
    private List<BaseEntity> citys = new ArrayList<>();
    //    private String sort[] = {"不限", "默认", "智能", "价格", "发布时间"};
    //    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private List<View> popupViews = new ArrayList<>();
    private GirdDropDownAdapter cityAdapter;
    private ListDropDownAdapter sortAdapter;
    private ListDropDownAdapter jobAdapter;
    private PartJobAdapter adapter;
    private int lastVisibleItem;
    private LinearLayoutManager mLayoutManager;
    public List<Jobs.ListTJobEntity> jobList = new ArrayList<Jobs.ListTJobEntity>();
    String typeid = "0";
    String areid = "0";
    String filterid = "2";

    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;
    private int MSG_CITY_SUCCESS = 2;
    private int MSG_CITY_FAIL = 3;
    BaseBean<CityCategory> cityCategoryBaseBean;
    private Handler mHandler = new Myhandler(this.getActivity());
    private DropDownMenu mMenu;
    private int mtype = 0;
    private boolean DataComplete=false;
    private String cityCode;

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
                    if (refreshLayout!=null&&refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
                    BaseBean<Jobs> jobs = (BaseBean<Jobs>) msg.obj;
                    int count = msg.arg1;
                    if (count == 0) {
                        jobList.clear();
                    }
                    jobs.getData().getList_t_job();
                    jobList.addAll(jobs.getData().getList_t_job());
                    adapter.notifyDataSetChanged();
                    DataComplete=true;
                    break;
                case 1:
                    //                    String ErrorMessage = (String) msg.obj;
                    //                    Toast.makeText(mainActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    cityCategoryBaseBean = (BaseBean<CityCategory>) msg.obj;
                    initDrawData(cityCategoryBaseBean);
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
        View view = inflater.inflate(R.layout.fragment_part_job, container, false);
        ButterKnife.inject(this, view);
        EventBus.getDefault().register(this);
        initData();
        initview();
        initDropDownView(view);
        return view;
    }

    private void initData() {
        cityCode = (String) SPUtils.getParam(getActivity(), Constants.USER_INFO, Constants.USER_LOCATION_CODE, "010");
        getCityCategory(cityCode);
    }

    private void initview() {
        tvTitle.setText("兼职");
        imgBack.setVisibility(View.GONE);
        adapter = new PartJobAdapter(jobList, getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        list.setLayoutManager(mLayoutManager);
        //设置adapter
        list.setAdapter(adapter);
        //设置Item增加、移除动画
        list.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        refreshLayout.setColorSchemeResources(R.color.app_bg);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJobs(cityCode,
                        typeid, areid
                        , "2", "0");
            }
        });
    }

    private void initDropDownView(View view) {



        //init sex menu
        mMenu = (DropDownMenu) view.findViewById(R.id.menu);
        mMenu.setmMenuCount(3);
        mMenu.setmShowCount(6);
        mMenu.setShowCheck(true);//是否显示展开list的选中项1
        mMenu.setmMenuTitleTextSize(12);//Menu的文字大小
        mMenu.setmMenuTitleTextColor(Color.BLACK);//Menu的文字颜色
        mMenu.setmMenuListTextSize(12);//Menu展开list的文字大小
        mMenu.setmMenuListTextColor(Color.BLACK);//Menu展开list的文字颜色
        mMenu.setmMenuBackColor(Color.WHITE);//Menu的背景颜色
        mMenu.setmUpArrow(R.drawable.arrow_up);//Menu默认状态的箭头
        mMenu.setmDownArrow(R.drawable.arrow_down);//Menu按下状态的箭头
        mMenu.setmCheckIcon(R.drawable.ico_make);//Menu展开list的勾选图片

//                mMenu.setDefaultMenuTitle(headers);//默认未选择任何过滤的Menu title
        mMenu.setMenuSelectedListener(new OnMenuSelectedListener() {
            @Override
            //Menu展开的list点击事件  RowIndex：list的索引  ColumnIndex：menu的索引
            public void onSelected(View listview, int RowIndex, int ColumnIndex,int sortId) {
                if (ColumnIndex == 0) {
                    typeid = String.valueOf(sortId);
                } else if (ColumnIndex == 2) {
                    switch (RowIndex) {
                        case 0:
                            filterid = "2";
                            break;
                        case 1:
                            filterid = "1";
                            break;
                        case 2:
                            filterid = "0";
                            break;
                    }
                } else if (ColumnIndex == 1) {
                    areid= String.valueOf(sortId);
                }
                getJobs(cityCode, typeid, areid, filterid, "0");
            }
        });
    }

    public void initDrawData(BaseBean<CityCategory> cityCategoryBaseBean) {
        List<List<BaseEntity>> items = new ArrayList<>();
        CityCategory.ListTCity2Entity listTCity2Entity = null;
        citys.clear();
        for (int i = 0; i < cityCategoryBaseBean.getData().getList_t_city2().size(); i++) {
            if (cityCategoryBaseBean.getData().getList_t_city2().get(i).getCode().equals(cityCode)){
                listTCity2Entity=cityCategoryBaseBean.getData().getList_t_city2().get(i);
                break;
            }
        }
        for (int i = 0; i < listTCity2Entity.getList_t_area().size(); i++) {
            BaseEntity baseEntity=new BaseEntity();
            baseEntity.setName(listTCity2Entity.getList_t_area().get(i).getArea_name());
            baseEntity.setId(listTCity2Entity.getList_t_area().get(i).getId());
            citys.add(baseEntity);
        }

        for (int i = 0; i < cityCategoryBaseBean.getData().getList_t_type().size(); i++) {
            BaseEntity baseEntity=new BaseEntity();
            baseEntity.setName(cityCategoryBaseBean.getData().getList_t_type().get(i).getType_name());
            baseEntity.setId(cityCategoryBaseBean.getData().getList_t_type().get(i).getId());
            jobs.add(baseEntity);
        }

        BaseEntity baseEntity=new BaseEntity();
        baseEntity.setName("推荐排序");
        baseEntity.setId(2);
        BaseEntity baseEntity1=new BaseEntity();
        baseEntity1.setName("最新发布");
        baseEntity1.setId(1);
        BaseEntity baseEntity2=new BaseEntity();
        baseEntity2.setName("工资最高");
        baseEntity2.setId(0);
        sort.add(baseEntity);
        sort.add(baseEntity2);
        sort.add(baseEntity1);
        //    sort.add("推荐排序");
        items.add(jobs);
        items.add(citys);
        items.add(sort);
        mMenu.setShowCheck(true);//是否显示展开list的选中项
        mMenu.setmMenuTitleTextSize(14);//Menu的文字大小
        mMenu.setmMenuTitleTextColor(Color.BLACK);//Menu的文字颜色
        mMenu.setmMenuListTextSize(12);//Menu展开list的文字大小
        mMenu.setmMenuListTextColor(Color.BLACK);//Menu展开list的文字颜色
        mMenu.setmMenuBackColor(Color.WHITE);//Menu的背景颜色
        mMenu.setmMenuPressedBackColor(getResources().getColor(R.color.gray_btn_bg_color));//Menu按下的背景颜色
        mMenu.setmUpArrow(R.drawable.arrow_up);//Menu默认状态的箭头
        mMenu.setmDownArrow(R.drawable.arrow_down);//Menu按下状态的箭头
        mMenu.setmCheckIcon(R.drawable.ico_make);//Menu展开list的勾选图片
        mMenu.setmMenuItems(items);
        getJobs(cityCode, typeid, areid, "2", "0");
    }


    private void resetDrawMenu(String cityCode) {
        List<List<BaseEntity>> items = new ArrayList<>();
        CityCategory.ListTCity2Entity listTCity2Entity = null;
        citys.clear();
        for (int i = 0; i < cityCategoryBaseBean.getData().getList_t_city2().size(); i++) {
            if (cityCategoryBaseBean.getData().getList_t_city2().get(i).getCode().equals(cityCode)){
                listTCity2Entity=cityCategoryBaseBean.getData().getList_t_city2().get(i);
                break;
            }
        }
        for (int i = 0; i < listTCity2Entity.getList_t_area().size(); i++) {
            BaseEntity baseEntity=new BaseEntity();
            baseEntity.setName(listTCity2Entity.getList_t_area().get(i).getArea_name());
            baseEntity.setId(listTCity2Entity.getList_t_area().get(i).getId());
            citys.add(baseEntity);
        }

        mMenu.setAreaText();
        getJobs(cityCode, typeid, areid, "2", "0");
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void onEvent(final JobFilterEvent event) {
        cityCode= String.valueOf(event.cityId);
        resetDrawMenu(cityCode);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (jobList.size() > 5 && lastVisibleItem == jobList.size() &&DataComplete) {
                    getJobs(cityCode, typeid, areid, filterid,String.valueOf(lastVisibleItem));
                    DataComplete=false;
                    LogUtils.e("position",lastVisibleItem+"开始");
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.img_back)
    public void onClick() {
    }

    /**
     * 获取城市列表和兼职种类
     */
    public void getCityCategory(String cityid) {
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        OkHttpUtils
                .get()
                .url(Constants.GET_USER_CITY_CATEGORY)
                .addParams("only", only)
                .addParams("login_id", "0")
                .addParams("city_id", "")
                .build()
                .connTimeOut(60000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new Callback<BaseBean<CityCategory>>() {
                    @Override
                    public BaseBean parseNetworkResponse(Response response,int id) throws Exception {
                        String string = response.body().string();
                        BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<CityCategory>>() {
                        }.getType());
                        return baseBean;
                    }

                    @Override
                    public void onError(Call call, Exception e,int id) {
                        Message message = new Message();
                        message.obj = e.toString();
                        message.what = MSG_CITY_FAIL;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(BaseBean baseBean,int id) {
                        if (baseBean.getCode().equals("200")) {
                            Message message = new Message();
                            message.obj = baseBean;
                            message.what = MSG_CITY_SUCCESS;
                            mHandler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.obj = baseBean.getMessage();
                            message.what = MSG_CITY_FAIL;
                            mHandler.sendMessage(message);
                        }
                    }

                });
    }


    /**
     * postInfo
     * @param cityCode
     * @param typeid
     * @param areid
     * @param filterid
     * @param count
     */
    public void getJobs(String cityCode, String typeid, String areid, String filterid, final String count) {
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        OkHttpUtils
                .get()
                .url(Constants.GET_JOB_CATEGORY)
                .addParams("only", only)
                .addParams("city_id", cityCode)
                .addParams("type_id", typeid)
                .addParams("area_id", areid)
                .addParams("filter_id", filterid)
                .addParams("count", count)
                .build()
                .connTimeOut(60000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new Callback<BaseBean<Jobs>>() {
                    @Override
                    public BaseBean parseNetworkResponse(Response response,int id) throws Exception {
                        String string = response.body().string();
                        BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<Jobs>>() {
                        }.getType());
                        return baseBean;
                    }

                    @Override
                    public void onError(Call call, Exception e,int id) {
                        Message message = new Message();
                        message.obj = e.toString();
                        message.what = MSG_GET_FAIL;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(BaseBean baseBean,int id) {
                        if (baseBean.getCode().equals("200")) {
                            //                                SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                            Message message = new Message();
                            message.obj = baseBean;
                            message.arg1 = Integer.parseInt(count);
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
