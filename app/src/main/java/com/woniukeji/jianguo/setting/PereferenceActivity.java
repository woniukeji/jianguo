package com.woniukeji.jianguo.setting;

import android.graphics.Canvas;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.CityCategory;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.SPUtils;
import com.woniukeji.jianguo.widget.DividerGridItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class PereferenceActivity extends BaseActivity {

    private String[] weeks={"时间","一","二","三","四","五","六","日","上午","","","","","","","","下午","","","","","","",""
    ,"晚上","","","","","","",""
    };
    private RecyclerView mRecyclerView;
    private RecyclerView mJobRecyclerView;
    BaseBean<CityCategory> cityCategoryBaseBean;
    private List<CityCategory.ListTTypeEntity> JobEntitys=new ArrayList<>();
    private JobAdapter jobAdapter;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_pereference);
    }

    @Override
    public void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_time);
        mJobRecyclerView = (RecyclerView) findViewById(R.id.recycler_job);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,8);
        GridLayoutManager jobLayoutManager=new GridLayoutManager(this,4);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mJobRecyclerView.setLayoutManager(jobLayoutManager);
        mRecyclerView.setAdapter(new GridAdapter());
         jobAdapter = new JobAdapter();
        mJobRecyclerView.setAdapter(jobAdapter);
        mJobRecyclerView.addItemDecoration(new DividerGridItemDecoration(this) {});
//添加分割线
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this) {
        });
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        String cityid = String.valueOf(SPUtils.getParam(PereferenceActivity.this, Constants.LOGIN_INFO, Constants.LOGIN_CITY_ID, 1));
        int position = (int) SPUtils.getParam(PereferenceActivity.this, Constants.LOGIN_INFO, Constants.LOGIN_CITY_POSITION, 0);
        getCityCategory(cityid);
    }

    @Override
    public void addActivity() {

    }

    @Override
    public void onClick(View v) {

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
                            jobAdapter.notifyDataSetChanged();
                        } else {
                            Message message = new Message();
                            message.obj = baseBean.getMessage();
                        }
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
                          holder.itemView.setOnClickListener(new View.OnClickListener()
                          {
                              @Override
                              public void onClick(View v)
                              {
                                  holder.imageView.setImageDrawable(getResources().getDrawable(R.mipmap.choose));
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
                holder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        holder.imageView.setImageDrawable(getResources().getDrawable(R.mipmap.choose));
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
