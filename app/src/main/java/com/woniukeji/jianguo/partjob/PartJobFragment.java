package com.woniukeji.jianguo.partjob;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseFragment;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.Jobs;
import com.woniukeji.jianguo.entity.User;
import com.woniukeji.jianguo.main.MainActivity;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.widget.FixedRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link } subclass.
 */
public class PartJobFragment extends BaseFragment {

private Context context=getActivity();
    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.dropDownMenu) com.yyydjk.library.DropDownMenu mDropDownMenu;
    @InjectView(R.id.list) FixedRecyclerView list;
    @InjectView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;
    private String headers[] = {"职业", "排序", "地区"};
    private String jobs[] = {"不限", "服务员", "厨师", "程序员", "摊煎饼", "城市猎人"};
    private String sort[] = {"不限", "默认", "智能", "价格", "发布时间"};
    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private List<View> popupViews = new ArrayList<>();
    private GirdDropDownAdapter cityAdapter;
    private ListDropDownAdapter sortAdapter;
    private ListDropDownAdapter jobAdapter;
    private PartJobAdapter adapter;
    public List<Jobs.ListTJobEntity> jobList = new ArrayList<Jobs.ListTJobEntity>();
    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;
    private Handler mHandler = new Myhandler(this.getActivity());

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
                    BaseBean<Jobs> jobs = (BaseBean<Jobs>) msg.obj;
                    jobs.getData().getList_t_job();
                   jobList.addAll(jobs.getData().getList_t_job());
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
//                    String ErrorMessage = (String) msg.obj;
//                    Toast.makeText(mainActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part_job, container, false);
        ButterKnife.inject(this, view);
        initDropDownView();
        initview();
        initData();
        return view;
    }

    private void initData() {
        GetTask getTask = new GetTask("0");
        getTask.execute();
    }

    private void initview() {
        tvTitle.setText("兼职");
        imgBack.setVisibility(View.GONE);
        adapter = new PartJobAdapter(jobList, getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
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
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });
    }

    private void initDropDownView() {
        final ListView cityView = new ListView(getActivity());
        cityAdapter = new GirdDropDownAdapter(getActivity(), Arrays.asList(citys));
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);
        final ListView sortView = new ListView(getActivity());
        sortView.setDividerHeight(0);
        sortAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(sort));
        sortView.setAdapter(sortAdapter);

        //init sex menu
        final ListView jobView = new ListView(getActivity());
        jobView.setDividerHeight(0);
        jobAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(jobs));
        jobView.setAdapter(jobAdapter);
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
                mDropDownMenu.closeMenu();
            }
        });

        sortView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sortAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : sort[position]);
                mDropDownMenu.closeMenu();
            }
        });

        jobView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jobAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : jobs[position]);
                mDropDownMenu.closeMenu();
            }
        });

        popupViews.add(cityView);
        popupViews.add(sortView);
        popupViews.add(jobView);

        TextView contentView = new TextView(getActivity());
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setText("内容显示区域");
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.img_back)
    public void onClick() {
    }


    public class GetTask extends AsyncTask<Void, Void, Void> {
        private final String type;

        GetTask(String type) {
            this.type = type;
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
            super.onPreExecute();
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
