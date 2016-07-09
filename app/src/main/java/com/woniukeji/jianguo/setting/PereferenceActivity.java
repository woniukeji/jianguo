package com.woniukeji.jianguo.setting;

import android.app.ProgressDialog;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.CityCategory;
import com.woniukeji.jianguo.entity.WeekTime;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.SPUtils;
import com.woniukeji.jianguo.widget.DividerGridItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class PereferenceActivity extends BaseActivity {

    private String[] weeks={"时间","一","二","三","四","五","六","日","上午","","","","","","","","下午","","","","","","",""
    ,"晚上","","","","","","",""
    };
    private List<WeekTime> WeekTimes =new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView mJobRecyclerView;
    BaseBean<CityCategory> cityCategoryBaseBean;
    private List<CityCategory.ListTTypeEntity> JobEntitys=new ArrayList<>();
    private List<CityCategory.ListTTypeEntity> JobEntityChooses=new ArrayList<>();
    private List<String> weekIds=new ArrayList<>();


    private List<Boolean> jobChooses=new ArrayList<Boolean>();

    private JobAdapter jobAdapter;
    private ProgressDialog progressDialog;
    private int loginId;
    private TextView tvSave;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_pereference);
    }

    @Override
    public void initViews() {
        tvSave = (TextView) findViewById(R.id.tv_save);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_time);
        mJobRecyclerView = (RecyclerView) findViewById(R.id.recycler_job);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,8);
        GridLayoutManager jobLayoutManager=new GridLayoutManager(this,4);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mJobRecyclerView.setLayoutManager(jobLayoutManager);
        mRecyclerView.setAdapter(new GridAdapter());
         jobAdapter = new JobAdapter();
        mJobRecyclerView.setAdapter(jobAdapter);
//        mJobRecyclerView.addItemDecoration(new DividerGridItemDecoration(this) {});
//添加分割线
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this) {
        });
    }

    @Override
    public void initListeners() {
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postHobbyInfo();
            }
        });
    }

    @Override
    public void initData() {
        initTimeData();
        String cityid = String.valueOf(SPUtils.getParam(PereferenceActivity.this, Constants.LOGIN_INFO, Constants.LOGIN_CITY_ID, 1));
        int position = (int) SPUtils.getParam(PereferenceActivity.this, Constants.LOGIN_INFO, Constants.LOGIN_CITY_POSITION, 0);
        loginId = (int) SPUtils.getParam(PereferenceActivity.this, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
        getCityCategory(cityid);
    }

    private void initTimeData() {
        for (int i = 0; i < weeks.length; i++) {
            if (weeks[i].equals("")){
                WeekTime weekTime=new WeekTime();
                weekTime.setId(0);
                weekTime.setName(weeks[i]);
            }

        }

    }

    @Override
    public void addActivity() {

    }

    @Override
    public void onClick(View v) {

    }


    public void postHobbyInfo(){
        progressDialog=new ProgressDialog(PereferenceActivity.this);
        progressDialog.setTitle("提交中");
        progressDialog.show();
        Map map=new HashMap();
        List tempList=new ArrayList();
        for (int i = 0; i <jobChooses.size() ; i++) {
            if (jobChooses.get(i)){
                tempList.add(JobEntitys.get(i).getId());
            }
        }
        map.put("list_t_type",tempList);
        String json=new Gson().toJson(map);

        Map map1=new HashMap();
        List tempList1=new ArrayList();
        for (int i = 0; i <weekIds.size() ; i++) {
            tempList1.add(weekIds.get(i));
        }
        map1.put("list_t_time",tempList1);
        String json1=new Gson().toJson(map1);

        postHobby(json,json1);
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
                .addParams("city_id", cityid)
                .build()
                .connTimeOut(60000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new Callback<BaseBean<CityCategory>>() {
                    @Override
                    public BaseBean parseNetworkResponse(Response response, int id) throws Exception {
                        String string = response.body().string();
                        BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<CityCategory>>() {
                        }.getType());
                        return baseBean;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Message message = new Message();
                        message.obj = e.toString();
                    }

                    @Override
                    public void onResponse(BaseBean baseBean,int id) {
                        if (baseBean.getCode().equals("200")) {
                            Message message = new Message();
                            message.obj = baseBean;
                            cityCategoryBaseBean = (BaseBean<CityCategory>)baseBean;
                            JobEntitys.addAll( cityCategoryBaseBean.getData().getList_t_type());
                            for (int i = 0; i < JobEntitys.size(); i++) {
                                jobChooses.add(false);
                            }
                            jobAdapter.notifyDataSetChanged();
                        } else {
                            Message message = new Message();
                            message.obj = baseBean.getMessage();
                        }
                    }

                });
    }
    /**
     * 获取城市列表和兼职种类
     */
    public void getHobby() {
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        OkHttpUtils
                .get()
                .url(Constants.GET_HOBBY_JOB)
                .addParams("only", only)
                .addParams("login_id", String.valueOf(loginId))
                .build()
                .connTimeOut(60000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new Callback<BaseBean<CityCategory>>() {
                    @Override
                    public BaseBean parseNetworkResponse(Response response, int id) throws Exception {
                        String string = response.body().string();
                        BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<CityCategory>>() {
                        }.getType());
                        return baseBean;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Message message = new Message();
                        message.obj = e.toString();
                    }

                    @Override
                    public void onResponse(BaseBean baseBean,int id) {
                        if (baseBean.getCode().equals("200")) {
                            Message message = new Message();
                            message.obj = baseBean;
                            cityCategoryBaseBean = (BaseBean<CityCategory>)baseBean;
                            JobEntitys.addAll( cityCategoryBaseBean.getData().getList_t_type());
                            for (int i = 0; i < JobEntitys.size(); i++) {
                                jobChooses.add(false);
                            }
                            jobAdapter.notifyDataSetChanged();
                        } else {
                            Message message = new Message();
                            message.obj = baseBean.getMessage();
                        }
                    }

                });
    }
    /**
     * 提交求职意向
     */
    public void postHobby(String jobJson,String weekJson) {
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        OkHttpUtils
                .get()
                .url(Constants.POST_HOBBY_JOB)
                .addParams("only", only)
                .addParams("login_id", String.valueOf(loginId))
                .addParams("json_type", jobJson)
                .addParams("json_time", weekJson)
                .build()
                .connTimeOut(60000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new Callback<BaseBean<CityCategory>>() {
                    @Override
                    public BaseBean parseNetworkResponse(Response response, int id) throws Exception {
                        String string = response.body().string();
                        BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<CityCategory>>() {
                        }.getType());
                        return baseBean;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Message message = new Message();
                        message.obj = e.toString();
                        Toast.makeText(PereferenceActivity.this,"发生错误，请重新设置",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onResponse(BaseBean baseBean,int id) {
                        if (baseBean.getCode().equals("200")) {
                            Message message = new Message();
                            message.obj = baseBean;
                        } else {
                            Message message = new Message();
                            message.obj = baseBean.getMessage();
                        }
                        Toast.makeText(PereferenceActivity.this,baseBean.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        finish();
                    }

                });
    }
   private   class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder>
    {
        public GridAdapter() {
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    PereferenceActivity.this).inflate(R.layout.item_grid_date, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position)
        {
            holder.tv.setText(weeks[position]);
                      if (!weeks[position].equals("")){
                          holder.itemView.setBackgroundResource(R.color.gray_e7);
                      }else {
                          for (int i = 0; i < weekIds.size(); i++) {
                              int tens= Integer.parseInt(weekIds.get(i).substring(0,1));//个位
                              int units= Integer.parseInt(weekIds.get(i).substring(0,1));//十位
                              int finalPosition=tens*8+units;
                              if (position==finalPosition){
                                  holder.imageView.setVisibility(View.VISIBLE);
                                  holder.imageView.setImageDrawable(getResources().getDrawable(R.mipmap.choose));
                              }
                          }

                          holder.itemView.setOnClickListener(new View.OnClickListener()
                          {
                              @Override
                              public void onClick(View v)
                              {
                                  if (holder.imageView.isShown()){
                                      String id=String.valueOf(position/8)+position%8;
                                      weekIds.remove(id);
                                      holder.imageView.setVisibility(View.GONE);
                                  }else {
                                      String id=String.valueOf(position/8)+position%8;
                                      weekIds.add(id);
                                      holder.imageView.setVisibility(View.VISIBLE);
                                      holder.imageView.setImageDrawable(getResources().getDrawable(R.mipmap.choose));
                                  }

                              }
                          });
                      }
        }
        @Override
        public int getItemCount() {
            return weeks.length;
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {
            TextView tv;
            ImageView imageView;

            public MyViewHolder(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.tv_week);
                imageView= (ImageView) view.findViewById(R.id.img_choose);
            }
        }
    }

    private   class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder>
    {
        public JobAdapter() {
        }
        @Override
        public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            JobViewHolder holder = new JobViewHolder(LayoutInflater.from(
                    PereferenceActivity.this).inflate(R.layout.item_grid_job, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final JobViewHolder holder, final int position)
        {
            holder.tv.setText(JobEntitys.get(position).getType_name());
            if (jobChooses.get(position)){
                holder.tv.setTextColor(getResources().getColor(R.color.white));
                holder.tv.setBackgroundColor(getResources().getColor(R.color.app_bg));
            }else {
                holder.tv.setTextColor(getResources().getColor(R.color.black_text));
                holder.tv.setBackgroundColor(getResources().getColor(R.color.white));
            }
                holder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        //当选中第一个为职业不限的时候其余职业状态置为未选中，当点击的item不是职业不限的时候，职业不限自动置为未选中
                        if (position==0){
                            if (!jobChooses.get(0)){
                                for (int i = 0; i < jobChooses.size(); i++) {
                                    if (jobChooses.get(i)){
                                        jobChooses.set(i,false);
                                    }
                                }
                                holder.tv.setTextColor(getResources().getColor(R.color.white));
                                holder.tv.setBackgroundColor(getResources().getColor(R.color.app_bg));
                                jobChooses.set(0,true);
                                notifyDataSetChanged();

                            }
                        }else {
                            if (jobChooses.get(position)){
                                jobChooses.set(position,false);
                                holder.tv.setTextColor(getResources().getColor(R.color.black_text));
                                holder.tv.setBackgroundColor(getResources().getColor(R.color.white));

                            }else {
                                jobChooses.set(position,true);
                                holder.tv.setTextColor(getResources().getColor(R.color.white));
                                holder.tv.setBackgroundColor(getResources().getColor(R.color.app_bg));
                            }
                            jobChooses.set(0,false);
                            notifyDataSetChanged();
                        }



                    }
                });
            }
        @Override
        public int getItemCount() {
            return JobEntitys.size();
        }

        class JobViewHolder extends RecyclerView.ViewHolder
        {
            TextView tv;
            ImageView imageView;

            public JobViewHolder(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.tv_job);
            }
        }
    }
}
